package com.ribsky.account.dialog.profile

import androidx.lifecycle.viewModelScope
import com.ribsky.account.dialog.base.BaseProfileViewModel
import com.ribsky.account.model.UserModel
import com.ribsky.core.Resource
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

            if (user != null) {
                val model = UserModel(
                    name = user.name,
                    email = "",
                    image = user.image,
                    isPrem = user.hasPrem,
                    streak = UserModel.StreakModel(
                        day = user.streak,
                        isStreak = false
                    ),
                    lessons = UserModel.LessonModel(
                        count = user.lessonsCount,
                        lessons = lessonInteractor.getLessons().size
                    ),
                    books = user.score,
                    stars = UserModel.StarsModel(
                        count = user.starsCount,
                        stars = lessonInteractor.getLessons().size * 3
                    ),
                    bio = mutableListOf<String>().apply {
                        getGoalByIdUseCase.invoke(user.bioGoal)?.name?.let { add(it) }
                        getLevelByIdUseCase.invoke(user.bioLevel)?.name?.let { add(it) }
                    }
                )
                _userStatus.value = Resource.success(model)
            } else {
                // todo
            }
        }
    }
}
