package com.ribsky.top.ui.streak

import androidx.lifecycle.viewModelScope
import com.ribsky.core.Resource
import com.ribsky.domain.usecase.time.GetLastTimeUseCase
import com.ribsky.domain.usecase.top.TopInteractor
import com.ribsky.domain.usecase.user.GetUserUseCase
import com.ribsky.top.model.TopModel
import com.ribsky.top.ui.base.BaseTopViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class TopStreakViewModel(
    getUserUseCase: GetUserUseCase,
    getLastTimeUseCase: GetLastTimeUseCase,
    private val topInteractor: TopInteractor,
) : BaseTopViewModel(getUserUseCase, getLastTimeUseCase) {
    override val type: TopModel.ViewType = TopModel.ViewType.STREAK

    override fun loadUsers() {
        viewModelScope.launch {
            _usersStatus.value = Resource.loading()
            val users = topInteractor.loadUsersByStreak()
                .mapIndexed { index, baseTopModel ->
                    TopModel(
                        baseTopModel,
                        index + 1,
                        type
                    )
                }
            delay(500)
            _usersStatus.value = Resource.success(users)
        }
    }
}
