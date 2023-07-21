package com.ribsky.top.ui.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ribsky.core.Resource
import com.ribsky.core.mapper.ResultMapper.Companion.asResource
import com.ribsky.domain.exceptions.Exceptions
import com.ribsky.domain.usecase.time.GetLastTimeUseCase
import com.ribsky.domain.usecase.user.GetUserUseCase
import com.ribsky.top.model.TopModel
import kotlinx.coroutines.launch

abstract class BaseTopViewModel(
    private val getUserUseCase: GetUserUseCase,
    private val getLastTimeUseCase: GetLastTimeUseCase,
) : ViewModel() {

    private val _userStatus: MutableLiveData<Resource<TopModel>> = MutableLiveData()
    val userStatus: LiveData<Resource<TopModel>> get() = _userStatus

    protected val _usersStatus: MutableLiveData<Resource<List<TopModel>>> = MutableLiveData()
    val usersStatus: LiveData<Resource<List<TopModel>>> get() = _usersStatus

    private val users get() = _usersStatus.value?.data

    val time = getLastTimeUseCase.invoke()

    abstract val type: TopModel.ViewType

    fun getUser() {
        viewModelScope.launch {
            _userStatus.value = Resource.loading()
            val result = getUserUseCase.invoke().asResource()
            if (result.isSuccess) {
                val model = TopModel(result.data!!, 0, type)
                val place = calculatePlace(model)
                val user = model.copy(position = place)
                _userStatus.value = Resource.success(user)
            } else {
                _userStatus.value = Resource.error(Exceptions.UnknownException())
            }
        }
    }

    private fun calculatePlace(user: TopModel): Int {
        val index = users!!.indexOf(
            users?.firstOrNull { it.image == user.image && it.name == user.name }
        )
        return if (index != -1) {
            index + 1
        } else {
            -1
        }
    }

    abstract fun loadUsers()
}
