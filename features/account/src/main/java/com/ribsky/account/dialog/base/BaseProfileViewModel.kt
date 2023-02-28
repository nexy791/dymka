package com.ribsky.account.dialog.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ribsky.account.model.LessonInfo
import com.ribsky.common.livedata.Resource
import com.ribsky.domain.model.user.BaseUserModel

abstract class BaseProfileViewModel : ViewModel() {

    protected val _userStatus: MutableLiveData<Resource<BaseUserModel>> = MutableLiveData()
    val userStatus: LiveData<Resource<BaseUserModel>> get() = _userStatus

    val user get() = _userStatus.value?.data

    protected val _testsStatus: MutableLiveData<Resource<Int>> = MutableLiveData()
    val bookStatus: LiveData<Resource<Int>> get() = _testsStatus

    protected val _lessonsStatus: MutableLiveData<Resource<LessonInfo>> = MutableLiveData()
    val lessonsStatus: LiveData<Resource<LessonInfo>> get() = _lessonsStatus
}
