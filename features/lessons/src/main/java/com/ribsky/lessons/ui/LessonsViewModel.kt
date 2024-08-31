package com.ribsky.lessons.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ribsky.billing.manager.SubManager
import com.ribsky.common.base.BaseViewModel
import com.ribsky.core.Resource
import com.ribsky.domain.model.lesson.BaseLessonModel
import com.ribsky.domain.model.paragraph.BaseParagraphModel
import com.ribsky.domain.model.top.BaseTopModel
import com.ribsky.domain.usecase.active.AddActiveLessonUseCase
import com.ribsky.domain.usecase.config.GetDiscountUseCase
import com.ribsky.domain.usecase.file.IsContentExistsUseCase
import com.ribsky.domain.usecase.lesson.LessonInteractor
import com.ribsky.domain.usecase.paragraph.ParagraphInteractor
import com.ribsky.domain.usecase.stars.AddStarsToLessonUseCase
import com.ribsky.domain.usecase.streak.IsTodayStreakUseCase
import com.ribsky.domain.usecase.streak.SetTodayStreakUseCase
import com.ribsky.domain.usecase.top.TopInteractor
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LessonsViewModel(
    private val lessonInteractor: LessonInteractor,
    private val paragraphInteractor: ParagraphInteractor,
    private val subManager: SubManager,
    private val addActiveLessonUseCase: AddActiveLessonUseCase,
    private val isContentExistsUseCase: IsContentExistsUseCase,
    private val setTodayStreakUseCase: SetTodayStreakUseCase,
    private val isTodayStreakUseCase: IsTodayStreakUseCase,
    private val getDiscountUseCase: GetDiscountUseCase,
    private val addStarsToLessonUseCase: AddStarsToLessonUseCase,
    private val topInteractor: TopInteractor,
) : BaseViewModel(subManager, getDiscountUseCase) {

    private val _lessonsStatus: MutableLiveData<Resource<List<BaseLessonModel>>> =
        MutableLiveData()
    val lessonsStatus: LiveData<Resource<List<BaseLessonModel>>> get() = _lessonsStatus

    private val _paragraphStatus: MutableLiveData<Resource<BaseParagraphModel>> =
        MutableLiveData()
    val paragraphStatus: LiveData<Resource<BaseParagraphModel>> get() = _paragraphStatus

    val lessons get() = _lessonsStatus.value?.data

    fun isFileExists(content: String) = isContentExistsUseCase.invoke(content)

    fun updateTodayStreak() {
        setTodayStreakUseCase.invoke()
    }

    fun getLessons(id: String) {
        viewModelScope.launch {
            _lessonsStatus.value = Resource.loading()
            val lessons = lessonInteractor.getLessons(id)
            delay(500)
            _lessonsStatus.value = Resource.success(lessons.sortedBy { it.sort })
            getParagraph(id)
        }
    }

    private fun getParagraph(id: String) {
        viewModelScope.launch {
            _paragraphStatus.value = Resource.loading()
            val paragraph = paragraphInteractor.getParagraph(id)
            _paragraphStatus.value = Resource.success(paragraph)
        }
    }


    fun updateLesson(paragraphId: String, id: String, stars: Float) {
        viewModelScope.launch {
            _lessonsStatus.value = Resource.loading()
            addActiveLessonUseCase.invoke(id)
            addStarsToLessonUseCase.invoke(id, stars.toInt())
            getLessons(paragraphId)
        }
    }


    fun isNeedToShowWillUp(callback: (Result<List<BaseTopModel>>) -> Unit) {
        viewModelScope.launch {
            val result = topInteractor.getUsersForUpByStars()
            if (result.size >= 2) callback.invoke(Result.success(result))
            else callback.invoke(Result.failure(Throwable("Bad result")))
        }
    }

    fun isNeedToShowMore(callback: (Result<List<BaseTopModel>>) -> Unit) {
        viewModelScope.launch {
            val result = topInteractor.getUsersForMoreUpByStars()
            if (result.size >= 2) callback.invoke(Result.success(result))
            else callback.invoke(Result.failure(Throwable("Bad result")))
        }
    }

    val isTodayStreak get() = isTodayStreakUseCase.invoke()
}
