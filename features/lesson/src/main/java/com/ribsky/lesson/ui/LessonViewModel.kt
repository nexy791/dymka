package com.ribsky.lesson.ui

import androidx.core.os.bundleOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ribsky.analytics.Analytics
import com.ribsky.billing.manager.SubManager
import com.ribsky.common.livedata.Event
import com.ribsky.common.livedata.Resource
import com.ribsky.common.livedata.ResultMapper.Companion.asResource
import com.ribsky.domain.model.content.BaseContentModel
import com.ribsky.domain.model.lesson.BaseLessonModel
import com.ribsky.domain.model.user.BaseUserModel
import com.ribsky.domain.usecase.lesson.GetLessonContentUseCase
import com.ribsky.domain.usecase.lesson.LessonInteractor
import com.ribsky.domain.usecase.user.GetUserUseCase
import com.ribsky.lesson.mapper.factory.ChatMapperFactory
import com.ribsky.lesson.model.ChatModel
import com.ribsky.lesson.utils.checker.base.AnswerCheckerImpl
import com.ribsky.lesson.utils.checker.factory.CheckerFactory
import com.ribsky.lesson.utils.checker.strategy.base.Checker
import kotlinx.coroutines.launch

class LessonViewModel(
    private val getLessonContentUseCase: GetLessonContentUseCase,
    private val lessonInteractor: LessonInteractor,
    private val getUserUseCase: GetUserUseCase,
    private val chatMapperFactory: ChatMapperFactory,
    private val checkerFactory: CheckerFactory,
    private val subManager: SubManager,
) : ViewModel() {

    var errorCount: Int = 0
    private var currentContentIndex = -1
    private var lessonId: String = ""

    private val _contentStatus: MutableLiveData<Resource<BaseContentModel>> = MutableLiveData()
    val contentStatus: LiveData<Resource<BaseContentModel>> get() = _contentStatus

    private val _userStatus: MutableLiveData<Resource<BaseUserModel>> = MutableLiveData()
    val userStatus: LiveData<Resource<BaseUserModel>> get() = _userStatus

    private val _lessonStatus: MutableLiveData<Resource<BaseLessonModel>> = MutableLiveData()
    val lessonStatus: LiveData<Resource<BaseLessonModel>> get() = _lessonStatus

    private val _chatStatus: MutableLiveData<List<ChatModel>> = MutableLiveData()
    val chatStatus: LiveData<List<ChatModel>> get() = _chatStatus

    private val _successEvent: Event<Boolean> = Event()
    val successEvent: LiveData<Boolean> get() = _successEvent

    private val _endEvent: Event<Boolean> = Event()
    val endEvent: LiveData<Boolean> get() = _endEvent

    private val _actionStatus: MutableLiveData<String> = MutableLiveData()
    val actionStatus: LiveData<String> get() = _actionStatus

    private val content get() = _contentStatus.value?.data!!.content
    private val answers get() = content.getOrNull(currentContentIndex)?.answers

    fun getLesson(lessonId: String) {
        this.lessonId = lessonId
        viewModelScope.launch {
            _lessonStatus.value = Resource.loading()
            val lesson = lessonInteractor.getLesson(lessonId)
            _lessonStatus.value = Resource.success(lesson)
        }
    }

    fun getContent(content: String) {
        viewModelScope.launch {
            _contentStatus.value = Resource.loading()
            try {
                _contentStatus.value = getLessonContentUseCase.invoke(content).asResource()
                goNext()
            } catch (ex: Exception) {
                _contentStatus.value = Resource.error(ex)
            }
        }
    }

    fun getUser() {
        viewModelScope.launch {
            _userStatus.value = Resource.loading()
            _userStatus.value = getUserUseCase.invoke().asResource()
        }
    }

    fun checkAnswer(answer: Any) {
        if (answerToText(answer).isBlank()) return
        val type = content.getOrNull(currentContentIndex)
        val checker = checkerFactory.create(type!!, answer, answers)
        sendAnswer(answer)
        processAnswerMsg(checker)
    }

    fun skipText() {
        goNext()
    }

    private fun sendAnswer(answer: Any) {
        val answerText = answerToText(answer)
        if (answerText.isBlank()) return
        val message = chatMapperFactory.createTextFromUser(answerText)
        addMessageToChat(message)
    }

    private fun answerToText(answer: Any): String {
        return when (answer) {
            is List<*> -> answer.joinToString()
            is ChatModel.Test.TestModel -> answer.text
            else -> answer.toString()
        }.trim()
    }

    private fun processAnswerMsg(checker: Checker) {
        if (AnswerCheckerImpl(checker).checkAnswer()) {
            _successEvent.value = true
            sendCorrectAnswer()
            goNext()
        } else {
            sendWrongAnswer()
        }
    }

    private fun goNext() {
        sendAction()
        sendNextContent()
        currentContentIndex++
        showNextAction()
    }

    private fun showNextAction() {
        val nextContent = content.getOrNull(currentContentIndex)
        val nextContentAction =
            (nextContent as? BaseContentModel.BaseContentType.Text?)?.action
        if (nextContentAction.isNullOrBlank()) {
            _actionStatus.value = "Продовжити"
        } else {
            _actionStatus.value = nextContentAction!!
        }
    }

    private fun sendAction() {
        val currentContent = content.getOrNull(currentContentIndex)
        val currentContentAction =
            (currentContent as? BaseContentModel.BaseContentType.Text?)?.action
        if (currentContentAction.isNullOrBlank()) return
        addMessageToChat(chatMapperFactory.createTextFromUser(currentContentAction))
    }

    private fun sendNextContent() {
        val nextContent = content.getOrNull(currentContentIndex + 1)
        if (nextContent != null) {
            addMessageToChat(chatMapperFactory.create(nextContent))
        } else {
            finishLesson()
        }
    }

    private fun finishLesson() {
        _endEvent.value = true
    }

    private fun sendCorrectAnswer() {
        Analytics.logEvent(
            Analytics.Event.LESSON_ANSWER_CORRECT,
            bundle = bundleOf("lesson_id" to lessonId)
        )
        val message = chatMapperFactory.createAnswer("\uD83C\uDF89 Все правильно!", true)
        addMessageToChat(message)
    }

    private fun sendWrongAnswer() {
        Analytics.logEvent(
            Analytics.Event.LESSON_ANSWER_INCORRECT,
            bundle = bundleOf("lesson_id" to lessonId)
        )
        errorCount++
        val message =
            chatMapperFactory.createAnswer(
                "\uD83D\uDE35\u200D\uD83D\uDCAB Спробуй інший варіант",
                false
            )
        addMessageToChat(message)
    }

    private fun addMessageToChat(message: ChatModel) {
        _chatStatus.value = _chatStatus.value.orEmpty() + message
    }

    fun hint(): String = when (val item = content.getOrNull(currentContentIndex)) {
        is BaseContentModel.BaseContentType.TestPick -> item.text.firstOrNull()?.translatedText?.filter { it.value }
            ?.map { it.text }
        else -> answers
    }?.joinToString(" • ").orEmpty()

    val isSub get() = subManager.isSub()
}
