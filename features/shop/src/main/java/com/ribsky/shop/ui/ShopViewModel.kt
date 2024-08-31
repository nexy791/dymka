package com.ribsky.shop.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ribsky.billing.manager.SubManager
import com.ribsky.common.base.BaseViewModel
import com.ribsky.core.Resource
import com.ribsky.core.mapper.ResultMapper.Companion.asResource
import com.ribsky.domain.model.top.BaseTopModel
import com.ribsky.domain.model.user.BaseUserModel
import com.ribsky.domain.usecase.config.GetDiscountUseCase
import com.ribsky.domain.usecase.top.TopInteractor
import com.ribsky.domain.usecase.user.GetUserUseCase
import com.ribsky.shop.model.CatModel
import kotlinx.coroutines.launch

class ShopViewModel(
    private val getDiscountUseCase: GetDiscountUseCase,
    private val subManager: SubManager,
    private val getUserUseCase: GetUserUseCase,
    private val topInteractor: TopInteractor,
) : BaseViewModel(subManager, getDiscountUseCase) {

    private val _userStatus = MutableLiveData<Resource<BaseUserModel>>()
    val userStatus: LiveData<Resource<BaseUserModel>> = _userStatus

    private val _topStatus = MutableLiveData<Resource<List<BaseTopModel>>>()
    val topStatus: LiveData<Resource<List<BaseTopModel>>> = _topStatus


    fun getUser() {
        viewModelScope.launch {
            val result = getUserUseCase.invoke().asResource()
            _userStatus.value = result
        }
    }

    fun getTop() {
        viewModelScope.launch {
            _topStatus.value = Resource.loading()
            val result = topInteractor.loadUsersByPremium()
            val user = _userStatus.value?.data

            val list = mutableListOf<BaseTopModel>()
            if (user?.hasPrem == true) list.add(CatModel(user))
            list.addAll(result.filter { it.image != user?.image && it.name != user?.name })
            _topStatus.value = Resource.success(list.take(15))
        }
    }

    val sku get() = subManager.getSku()

}
