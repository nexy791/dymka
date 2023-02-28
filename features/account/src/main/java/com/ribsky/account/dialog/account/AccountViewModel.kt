package com.ribsky.account.dialog.account

import androidx.lifecycle.viewModelScope
import com.ribsky.account.dialog.base.BaseProfileViewModel
import com.ribsky.account.model.LessonInfo
import com.ribsky.common.livedata.ResultMapper.Companion.asResource
import com.ribsky.common.livedata.Resource
import com.ribsky.domain.usecase.active.GetActiveLessonsUseCase
import com.ribsky.domain.usecase.lesson.LessonInteractor
import com.ribsky.domain.usecase.score.GetTestScoreUseCase
import com.ribsky.domain.usecase.user.GetUserUseCase
import kotlinx.coroutines.launch

class AccountViewModel(
    private val getUserUseCase: GetUserUseCase,
    private val lessonInteractor: LessonInteractor,
    private val getActiveLessonsUseCase: GetActiveLessonsUseCase,
    private val getTestScoreUseCase: GetTestScoreUseCase,
) : BaseProfileViewModel() {

    fun getProfile() {
        viewModelScope.launch {
            _userStatus.value = Resource.loading()
            val result = getUserUseCase.invoke().asResource()
            _userStatus.value = result
            if (result.isSuccess) {
                getLessons()
                getTests()
            }
        }
    }

    private fun getTests() {
        viewModelScope.launch {
            _testsStatus.value = Resource.loading()
            _testsStatus.value = Resource.success(getTestScoreUseCase.invoke())
        }
    }

    private fun getLessons() {
        viewModelScope.launch {
            _lessonsStatus.value = Resource.loading()

            val activeLessons = getActiveLessonsUseCase.invoke().size
            val lessons = lessonInteractor.getLessons().size

            _lessonsStatus.value = Resource.success(LessonInfo(activeLessons, lessons))
        }
    }
}
