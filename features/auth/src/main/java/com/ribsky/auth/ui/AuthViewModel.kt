package com.ribsky.auth.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ribsky.core.Resource
import com.ribsky.core.mapper.ResultMapper.Companion.asResource
import com.ribsky.domain.usecase.auth.AuthUseCase
import com.ribsky.domain.usecase.clear.ClearDataUseCase
import kotlinx.coroutines.launch

class AuthViewModel(
    private val authUseCase: AuthUseCase,
    private val clearDataUseCase: ClearDataUseCase,
) : ViewModel() {

    private val _authStatus: MutableLiveData<Resource<Unit>> = MutableLiveData()
    val authStatus: LiveData<Resource<Unit>> get() = _authStatus

    fun authUser(idToken: String) {
        viewModelScope.launch {
            _authStatus.value = Resource.loading()
            _authStatus.value = authUseCase.invoke(idToken).asResource()
        }
    }

    fun clear() {
        viewModelScope.launch {
            clearDataUseCase.invoke()
        }
    }
}
