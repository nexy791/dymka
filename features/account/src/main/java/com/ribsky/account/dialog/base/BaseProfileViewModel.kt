package com.ribsky.account.dialog.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ribsky.account.model.UserModel
import com.ribsky.core.Resource

abstract class BaseProfileViewModel : ViewModel() {

    protected val _userStatus: MutableLiveData<Resource<UserModel>> = MutableLiveData()
    val userStatus: LiveData<Resource<UserModel>> get() = _userStatus

    val userModel get() = _userStatus.value?.data

}
