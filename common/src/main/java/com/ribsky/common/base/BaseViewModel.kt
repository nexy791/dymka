package com.ribsky.common.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.ribsky.billing.BillingState
import com.ribsky.billing.manager.SubManager
import com.ribsky.core.Resource
import com.ribsky.domain.usecase.config.GetDiscountUseCase
import kotlinx.coroutines.launch
import org.koin.java.KoinJavaComponent.inject

abstract class BaseViewModel(
    private val subManager: SubManager,
    private val getDiscountUseCase: GetDiscountUseCase,
) : ViewModel() {

    private val firebaseAuth: FirebaseAuth by inject(FirebaseAuth::class.java)

    protected val _discountStatus = MutableLiveData<Resource<BillingState>>()
    val discountStatus: LiveData<Resource<BillingState>> get() = _discountStatus
    val discount get() = _discountStatus.value?.data

    init {
        getIsFreeDiscountAvailable()
    }

    private fun getIsFreeDiscountAvailable() {
        _discountStatus.value = Resource.loading()
        viewModelScope.launch {

            if (subManager.isDiscount()) {
                _discountStatus.value = Resource.success(BillingState.Infinite())
                return@launch
            }

            val registerDate = (firebaseAuth.currentUser?.metadata?.creationTimestamp ?: 0) / 1000 / 60 / 60
            val current = System.currentTimeMillis() / 1000 / 60 / 60
            val needToWait = 36 // hours

            val diff = needToWait - ((current - registerDate))


            // if user registered less than 36 hours ago

            if (diff in 1 until needToWait + 1) {
                _discountStatus.value = Resource.success(BillingState.WelcomeDiscount((diff).toString()))
                return@launch
            }

            val result = getDiscountUseCase.invoke()
            result.fold(
                onSuccess = { _discountStatus.value = Resource.success(BillingState.Discount(it)) },
                onFailure = { _discountStatus.value = Resource.success(BillingState.NoDiscount) }
            )
        }
    }

    fun isNeedToShowPayWall(callback: (Result<String>) -> Unit) {
        viewModelScope.launch {
            val random = (0..2).random()
            if (random == 0 || random == 1) {
                callback.invoke(Result.failure(Throwable("Bad random")))
                return@launch
            }
            if (subManager.isSub()) {
                callback.invoke(Result.failure(Throwable("Bad Sub")))
                return@launch
            }

            when (val discount = discount) {
                is BillingState.Discount -> {
                    callback.invoke(Result.success(discount.date))
                    return@launch
                }
                is BillingState.Infinite -> {
                    callback.invoke(Result.success(discount.date))
                    return@launch
                }
                is BillingState.WelcomeDiscount -> {
                    callback.invoke(Result.success(discount.date))
                    return@launch
                }
                is BillingState.NoDiscount -> {
                    callback.invoke(Result.failure(Throwable("Bad discount")))
                    return@launch
                }

                null -> {
                    callback.invoke(Result.failure(Throwable("Bad discount")))
                    return@launch
                }
            }
        }
    }

    val isSub get() = subManager.isSub()

}