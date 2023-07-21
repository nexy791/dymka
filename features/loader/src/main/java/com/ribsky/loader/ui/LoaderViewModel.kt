package com.ribsky.loader.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ribsky.core.Resource
import com.ribsky.core.mapper.ResultMapper.Companion.asResource
import com.ribsky.domain.usecase.data.LoadDataUseCase
import com.ribsky.domain.usecase.user.GetUserUseCase
import com.ribsky.domain.usecase.user.SyncUserUseCase
import kotlinx.coroutines.launch

class LoaderViewModel(
    private val loadDataUseCase: LoadDataUseCase,
    private val syncUserUseCase: SyncUserUseCase,
    private val getUserUseCase: GetUserUseCase,
) : ViewModel() {

    private val _status: MutableLiveData<Resource<Unit>> = MutableLiveData()
    val status: LiveData<Resource<Unit>> get() = _status

    fun loadData() {
        viewModelScope.launch {
            _status.value = Resource.loading()

            val result = getUserUseCase.invoke()
            result.fold(
                onSuccess = {
                    sync()
                    _status.value = loadDataUseCase.invoke().asResource()
                },
                onFailure = {
                    _status.value = Resource.error(it)
                }
            )
        }
    }

    private suspend fun sync() {
        syncUserUseCase.invoke()
    }
}
