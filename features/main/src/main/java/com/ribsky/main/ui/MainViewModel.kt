package com.ribsky.main.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ribsky.billing.manager.SubManager
import com.ribsky.common.livedata.Resource
import com.ribsky.common.livedata.ResultMapper.Companion.asResource
import com.ribsky.domain.model.user.BaseUserModel
import com.ribsky.domain.usecase.sp.SharedPrefsInteractor
import com.ribsky.domain.usecase.user.GetUserUseCase
import kotlinx.coroutines.launch

class MainViewModel(
    private val getUserUseCase: GetUserUseCase,
    private val subManager: SubManager,
    private val sharedPrefsInteractor: SharedPrefsInteractor,
) : ViewModel() {

    private val _userStatus: MutableLiveData<Resource<BaseUserModel>> =
        MutableLiveData()
    val userStatus: LiveData<Resource<BaseUserModel>> get() = _userStatus

    val isShouldShowRateDialog: Boolean get() = sharedPrefsInteractor.isShouldShowRateDialog()

    val isSub get() = subManager.isSub()

    fun getProfile() {
        viewModelScope.launch {
            _userStatus.value = Resource.loading()
            _userStatus.value = getUserUseCase.invoke().asResource()
        }
    }
}
