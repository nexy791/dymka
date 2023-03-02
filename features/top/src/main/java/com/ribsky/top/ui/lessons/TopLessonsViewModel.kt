package com.ribsky.top.ui.lessons

import androidx.lifecycle.viewModelScope
import com.ribsky.common.livedata.Resource
import com.ribsky.domain.usecase.time.GetLastTimeUseCase
import com.ribsky.domain.usecase.top.TopInteractor
import com.ribsky.domain.usecase.user.GetUserUseCase
import com.ribsky.top.model.TopModel
import com.ribsky.top.ui.base.BaseTopViewModel
import kotlinx.coroutines.launch

class TopLessonsViewModel(
    getUserUseCase: GetUserUseCase,
    getLastTimeUseCase: GetLastTimeUseCase,
    private val topInteractor: TopInteractor,
) : BaseTopViewModel(getUserUseCase, getLastTimeUseCase) {

    override val type: TopModel.ViewType = TopModel.ViewType.LESSON

    override fun loadUsers() {
        viewModelScope.launch {
            _usersStatus.value = Resource.loading()
            val users = topInteractor.loadUsersByLessons()
                .mapIndexed { index, baseTopModel ->
                    TopModel(
                        baseTopModel,
                        index + 1,
                        type
                    )
                }
            _usersStatus.value = Resource.success(users)
        }
    }
}
