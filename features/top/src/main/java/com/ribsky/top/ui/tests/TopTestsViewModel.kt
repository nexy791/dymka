package com.ribsky.top.ui.tests

import androidx.lifecycle.viewModelScope
import com.ribsky.common.livedata.Resource
import com.ribsky.domain.usecase.sp.SharedPrefsInteractor
import com.ribsky.domain.usecase.top.TopInteractor
import com.ribsky.domain.usecase.user.GetUserUseCase
import com.ribsky.top.model.TopModel
import com.ribsky.top.ui.base.BaseTopViewModel
import kotlinx.coroutines.launch

class TopTestsViewModel(
    getUserUseCase: GetUserUseCase,
    sharedPrefsInteractor: SharedPrefsInteractor,
    private val topInteractor: TopInteractor,
) : BaseTopViewModel(getUserUseCase, sharedPrefsInteractor) {
    override val type: TopModel.ViewType = TopModel.ViewType.TEST

    override fun loadUsers() {
        viewModelScope.launch {
            _usersStatus.value = Resource.loading()
            val users = topInteractor.loadUsersByScore()
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
