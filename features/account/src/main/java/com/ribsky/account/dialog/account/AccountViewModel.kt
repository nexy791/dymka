package com.ribsky.account.dialog.account

import androidx.lifecycle.viewModelScope
import com.ribsky.account.dialog.base.BaseProfileViewModel
import com.ribsky.account.model.LessonInfo
import com.ribsky.core.Resource
import com.ribsky.core.mapper.ResultMapper.Companion.asResource
import com.ribsky.domain.usecase.active.GetActiveLessonsUseCase
import com.ribsky.domain.usecase.bio.GetCurrentGoalBioUseCase
import com.ribsky.domain.usecase.bio.GetCurrentLevelBioUseCase
import com.ribsky.domain.usecase.lesson.LessonInteractor
import com.ribsky.domain.usecase.score.GetTestScoreUseCase
import com.ribsky.domain.usecase.streak.GetCurrentStreakUseCase
import com.ribsky.domain.usecase.streak.IsTodayStreakUseCase
import com.ribsky.domain.usecase.user.GetUserUseCase
import kotlinx.coroutines.launch

class AccountViewModel(
    private val getUserUseCase: GetUserUseCase,
    private val lessonInteractor: LessonInteractor,
    private val getActiveLessonsUseCase: GetActiveLessonsUseCase,
    private val getTestScoreUseCase: GetTestScoreUseCase,
    private val getStreakUseCase: GetCurrentStreakUseCase,
    private val isTodayStreakUseCase: IsTodayStreakUseCase,
    private val getGoalBioUseCase: GetCurrentGoalBioUseCase,
    private val getLevelBioUseCase: GetCurrentLevelBioUseCase,
) : BaseProfileViewModel() {

    fun getProfile() {
        viewModelScope.launch {
            _userStatus.value = Resource.loading()
            val result = getUserUseCase.invoke().asResource()
            _userStatus.value = result
            if (result.isSuccess) {
                getLessons()
                getTests()
                getStreak()
                getChips()
            }
        }
    }

    private fun getStreak() {
        viewModelScope.launch {
            _streakStatus.value = Resource.loading()
            val result = getStreakUseCase.invoke()
            val isToday = isTodayStreakUseCase.invoke()
            _streakStatus.value = Resource.success(StreakModel(result, isToday))
        }
    }

    private fun getChips() {
        viewModelScope.launch {
            _chipsStatus.value = Resource.loading()
            val goal = getGoalBioUseCase.invoke()
            val level = getLevelBioUseCase.invoke()

            val list = mutableListOf<String>()
            level?.let { list.add(it.name) }
            goal?.let { list.add(it.name) }
            _chipsStatus.value = Resource.success(list)
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
