package com.ribsky.bot.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ribsky.analytics.Analytics
import com.ribsky.billing.manager.SubManager
import com.ribsky.bot.helper.bot.BotHelper
import com.ribsky.bot.helper.bot.BotHelperImpl
import com.ribsky.bot.helper.reply.SmartReplyHelper
import com.ribsky.bot.helper.reply.SmartReplyHelperImpl
import com.ribsky.bot.helper.translator.TranslatorHelper
import com.ribsky.bot.helper.translator.TranslatorHelperImpl
import com.ribsky.bot.model.ChatModel
import com.ribsky.core.Resource
import com.ribsky.core.mapper.ResultMapper.Companion.asResource
import com.ribsky.domain.model.user.BaseUserModel
import com.ribsky.domain.usecase.bot.AddBotScoreUseCase
import com.ribsky.domain.usecase.bot.CanBotReplyUseCase
import com.ribsky.domain.usecase.bot.GetBotScoreForTodayUseCase
import com.ribsky.domain.usecase.config.GetBotTokenUseCase
import com.ribsky.domain.usecase.user.GetUserUseCase
import com.ribsky.domain.usecase.user.SyncUserUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class BotViewModel(
    private val getUserUseCase: GetUserUseCase,
    private val subManager: SubManager,
    private val addBotScoreUseCase: AddBotScoreUseCase,
    private val canBotReplyUseCase: CanBotReplyUseCase,
    private val getBotScoreForTodayUseCase: GetBotScoreForTodayUseCase,
    private val syncUserUseCase: SyncUserUseCase,
    private val getBotTokenUseCase: GetBotTokenUseCase,
) : ViewModel() {

    private val _chatStatus: MutableLiveData<Resource<List<ChatModel>>> = MutableLiveData()
    val chatStatus: LiveData<Resource<List<ChatModel>>> get() = _chatStatus

    private val _botStatus: MutableLiveData<Resource<Unit>> = MutableLiveData()
    val botStatus: LiveData<Resource<Unit>> get() = _botStatus

    private val _userStatus: MutableLiveData<Resource<BaseUserModel>> = MutableLiveData()
    val userStatus: LiveData<Resource<BaseUserModel>> get() = _userStatus

    private val _repliesStatus: MutableLiveData<Resource<List<String>>> = MutableLiveData()
    val repliesStatus: LiveData<Resource<List<String>>> get() = _repliesStatus

    private val _scoreStatus: MutableLiveData<Int> = MutableLiveData(0)
    val scoreStatus: LiveData<Int> get() = _scoreStatus

    private val _syncStatus: MutableLiveData<Resource<Unit>> = MutableLiveData()
    val syncStatus: LiveData<Resource<Unit>> get() = _syncStatus

    private val botHelper: BotHelper by lazy { BotHelperImpl() }
    private val translatorHelper: TranslatorHelper by lazy { TranslatorHelperImpl() }
    private val smartReplyHelper: SmartReplyHelper by lazy { SmartReplyHelperImpl() }

    fun getBot() {
        viewModelScope.launch {
            val result = getBotTokenUseCase.invoke()
            result.fold(
                onSuccess = {
                    botHelper.init(it)
                    translatorHelper.init()
                    smartReplyHelper.init()
                    _botStatus.value = Resource.success(Unit)
                },
                onFailure = {
                    _botStatus.value = Resource.error(it)
                }
            )
        }
    }

    fun getUser() {
        viewModelScope.launch {
            _userStatus.value = Resource.loading()
            _userStatus.value = getUserUseCase.invoke().asResource()
        }
    }

    fun getDefaultChat() {
        viewModelScope.launch {
            _chatStatus.value = Resource.success(
                listOf(
                    ChatModel.Bot("Привіт! \uD83D\uDC4B"),
                    ChatModel.Bot("Мене звати Думка, тут ти можеш поставити мені будь-яке питання з української мови або літератури \uD83C\uDDFA\uD83C\uDDE6"),
                    ChatModel.Bot("А я з радістю на нього відповім! \uD83D\uDE0A"),
                    ChatModel.Bot("Але іноді я можу помилятись і відповідати не зовсім правильно \uD83D\uDE39"),

                    )
            )
        }
    }

    fun sendQuestion(question: String) {
        Analytics.logEvent(Analytics.Event.BOT_QUESTION)
        val list = _chatStatus.value?.data.orEmpty() + ChatModel.User(question)
        _chatStatus.value = Resource.success(list)

        if (!canBotReplyUseCase.invoke()) {
            _chatStatus.value =
                Resource.success(list + ChatModel.Bot("Сьогодні я вже відповідав на 10 питань, приходь завтра! \uD83D\uDE0A"))
            return
        }

        viewModelScope.launch {
            delay(300)
            _chatStatus.value = Resource.loading(list)
            botHelper.getAnswer(
                question,
                callback = {
                    Analytics.logEvent(Analytics.Event.BOT_ANSWER)
                    addBotScore()
                    updateSmartReply(question, it)
                    viewModelScope.launch {
                        delay(300)
                        _chatStatus.value = Resource.success(list + ChatModel.Bot(it))
                    }
                },
                error = {
                    Analytics.logEvent(Analytics.Event.BOT_ANSWER_ERROR)
                    updateSmartReply(question, it)
                    viewModelScope.launch {
                        delay(300)
                        _chatStatus.value = Resource.success(list + ChatModel.Bot(it))
                    }
                }
            )
        }
    }

    private fun updateSmartReply(question: String, answer: String) {

        viewModelScope.launch {
            _repliesStatus.value = Resource.loading()
            val question = translatorHelper.translate(question, TranslatorHelper.Language.UKRAINIAN)
            val answer = translatorHelper.translate(answer, TranslatorHelper.Language.UKRAINIAN)

            if (question.isFailure || answer.isFailure) {
                _repliesStatus.value = Resource.error(Throwable("Error translating"))
                return@launch
            }

            smartReplyHelper.addMessage(question.getOrThrow(), true)
            smartReplyHelper.addMessage(answer.getOrThrow(), false)

            val replies = smartReplyHelper.getReplies()

            if (replies.isFailure) {
                _repliesStatus.value =
                    Resource.error(replies.exceptionOrNull() ?: Throwable("Error getting replies"))
                return@launch
            }

            val repliesTranslated = mutableListOf<String>()
            replies.getOrThrow().forEach { reply ->
                val replyTranslated =
                    translatorHelper.translate(reply, TranslatorHelper.Language.ENGLISH)
                if (replyTranslated.isSuccess) {
                    var reply = replyTranslated.getOrThrow()
                    reply = reply.replace(",", "")
                    reply = reply.replace(".", "")
                    reply = reply.replace("!", "")
                    if (reply == "Великий") reply = "Чудово"
                    if (reply == "Хороший") reply = "Добре"
                    repliesTranslated.add(reply)
                } else {
                    Analytics.logEvent(Analytics.Event.BOT_REPLY_ERROR)
                    _repliesStatus.value = Resource.error(
                        replyTranslated.exceptionOrNull() ?: Throwable("Error translating")
                    )
                    return@launch
                }
            }

            _repliesStatus.value = Resource.success(repliesTranslated)
        }
    }

    private fun addBotScore() {
        viewModelScope.launch {
            addBotScoreUseCase.invoke()
            getScore()
        }
    }

    fun getScore() {
        _scoreStatus.value = getBotScoreForTodayUseCase.invoke()
    }

    fun sync() {
        viewModelScope.launch {
            syncUserUseCase.invoke()
            _syncStatus.value = Resource.success(Unit)
        }
    }

    val isBotCanReply get() = canBotReplyUseCase.invoke()

    val isSub get() = subManager.isSub()
}
