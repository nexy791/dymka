package com.ribsky.account.dialog.profile

import androidx.lifecycle.viewModelScope
import com.ribsky.account.dialog.base.BaseProfileViewModel
import com.ribsky.account.model.LessonInfo
import com.ribsky.account.model.UserModel
import com.ribsky.common.livedata.Resource
import com.ribsky.domain.usecase.bio.GetGoalByIdUseCase
import com.ribsky.domain.usecase.bio.GetLevelByIdUseCase
import com.ribsky.domain.usecase.lesson.LessonInteractor
import com.ribsky.domain.usecase.top.TopInteractor
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val topInteractor: TopInteractor,
    private val lessonInteractor: LessonInteractor,
    private val getLevelByIdUseCase: GetLevelByIdUseCase,
    private val getGoalByIdUseCase: GetGoalByIdUseCase,
) : BaseProfileViewModel() {

    fun getProfile(id: Int) {
        viewModelScope.launch {
            _userStatus.value = Resource.loading()
            val user = topInteractor.getPlayer(id)
            _userStatus.value = Resource.success(UserModel(user))
            getLessons(user.lessonsCount)
            getTests(user.score)
            getStreak(user.streak)
            getChips(user.bioLevel, user.bioGoal)
        }
    }

    private fun getStreak(streak: Int) {
        viewModelScope.launch {
            _streakStatus.value = Resource.loading()
            _streakStatus.value = Resource.success(StreakModel(streak, false))
        }
    }

    private fun getChips(bioLevel: Int, bioGoal: Int) {
        viewModelScope.launch {
            _chipsStatus.value = Resource.loading()
            val goal = getGoalByIdUseCase.invoke(bioGoal)
            val level = getLevelByIdUseCase.invoke(bioLevel)

            val list = mutableListOf<String>()
            level?.let { list.add(it.name) }
            goal?.let { list.add(it.name) }
            _chipsStatus.value = Resource.success(list)
        }
    }

    private fun getLessons(active: Int) {
        viewModelScope.launch {
            _lessonsStatus.value = Resource.loading()
            val lessons = lessonInteractor.getLessons().size
            _lessonsStatus.value = Resource.success(LessonInfo(active, lessons))
        }
    }

    private fun getTests(active: Int) {
        viewModelScope.launch {
            _testsStatus.value = Resource.loading()
            _testsStatus.value = Resource.success(active)
        }
    }
}
