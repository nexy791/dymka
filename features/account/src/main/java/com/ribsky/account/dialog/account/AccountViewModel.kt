package com.ribsky.account.dialog.account

import androidx.lifecycle.viewModelScope
import com.ribsky.account.dialog.base.BaseProfileViewModel
import com.ribsky.account.model.UserModel
import com.ribsky.core.Resource
import com.ribsky.domain.usecase.bio.GetGoalByIdUseCase
import com.ribsky.domain.usecase.bio.GetLevelByIdUseCase
import com.ribsky.domain.usecase.lesson.LessonInteractor
import com.ribsky.domain.usecase.streak.GetCurrentStreakUseCase
import com.ribsky.domain.usecase.streak.IsTodayStreakUseCase
import com.ribsky.domain.usecase.user.GetUserUseCase
import kotlinx.coroutines.launch

class AccountViewModel(
    private val getUserUseCase: GetUserUseCase,
    private val lessonInteractor: LessonInteractor,
    private val getStreakUseCase: GetCurrentStreakUseCase,
    private val isTodayStreakUseCase: IsTodayStreakUseCase,
    private val getLevelByIdUseCase: GetLevelByIdUseCase,
    private val getGoalByIdUseCase: GetGoalByIdUseCase,
) : BaseProfileViewModel() {

    fun getProfile() {
        viewModelScope.launch {
            _userStatus.value = Resource.loading()
            val userResult = getUserUseCase.invoke()

            if (userResult.isSuccess) {
                val user = userResult.getOrNull()!!
                val model = UserModel(
                    name = user.name,
                    email = user.email,
                    image = user.image,
                    isPrem = user.hasPrem,
                    streak = UserModel.StreakModel(
                        day = getStreakUseCase.invoke(),
                        isStreak = isTodayStreakUseCase.invoke()
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
                _userStatus.value =
                    Resource.error(userResult.exceptionOrNull() ?: Exception("Unknown error"))
            }

        }
    }
}
