package com.ribsky.loader.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ribsky.core.Resource
import com.ribsky.core.mapper.ResultMapper.Companion.asResource
import com.ribsky.domain.model.user.BaseUserModel
import com.ribsky.domain.usecase.data.LoadDataUseCase
import com.ribsky.domain.usecase.user.GetUserUseCase
import com.ribsky.domain.usecase.user.SyncUserUseCase
import kotlinx.coroutines.launch

class LoaderViewModel(
    private val loadDataUseCase: LoadDataUseCase,
    private val syncUserUseCase: SyncUserUseCase,
    private val getUserUseCase: GetUserUseCase,
) : ViewModel() {

    private val _statusData: MutableLiveData<Resource<Unit>> = MutableLiveData()
    val statusData: LiveData<Resource<Unit>> get() = _statusData

    private val _statusUser: MutableLiveData<Resource<BaseUserModel>> = MutableLiveData()
    val statusUser: LiveData<Resource<BaseUserModel>> get() = _statusUser

    fun loadUser() {
        viewModelScope.launch {
            _statusUser.value = Resource.loading()
            _statusUser.value = getUserUseCase.invoke().asResource()
        }
    }

    fun loadData() {
        viewModelScope.launch {
            _statusData.value = Resource.loading()
            sync()
            _statusData.value = loadDataUseCase.invoke().asResource()
        }
    }

    private suspend fun sync() {
        syncUserUseCase.invoke()
    }
}
