package com.ribsky.shop.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ribsky.billing.manager.SubManager
import com.ribsky.common.livedata.Resource
import com.ribsky.common.livedata.ResultMapper.Companion.asResource
import com.ribsky.domain.model.user.BaseUserModel
import com.ribsky.domain.usecase.config.GetDiscountUseCase
import com.ribsky.domain.usecase.user.GetUserUseCase
import kotlinx.coroutines.launch

class ShopViewModel(
    private val getDiscountUseCase: GetDiscountUseCase,
    private val subManager: SubManager,
    private val getUserUseCase: GetUserUseCase,
) : ViewModel() {

    private val _discountStatus = MutableLiveData<Resource<String>>()
    val discountStatus: LiveData<Resource<String>> = _discountStatus

    private val _userStatus = MutableLiveData<Resource<BaseUserModel>>()
    val userStatus: LiveData<Resource<BaseUserModel>> = _userStatus

    fun getUser() {
        viewModelScope.launch {
            val result = getUserUseCase.invoke().asResource()
            _userStatus.value = result
        }
    }

    fun getIsFreeDiscountAvailable() {
        viewModelScope.launch {

            if (subManager.isDiscount()) {
                _discountStatus.value = Resource.success("Назавжди ∞")
                return@launch
            }

            val result = getDiscountUseCase.invoke()
            result.fold(
                onSuccess = {
                    _discountStatus.value = Resource.success("до $it")
                },
                onFailure = {
                    _discountStatus.value = Resource.error(it)
                }
            )
        }
    }

    val isSub get() = subManager.isSub()

    val sku get() = subManager.getSku()

    val isDiscount get() = _discountStatus.value?.data != null


}
