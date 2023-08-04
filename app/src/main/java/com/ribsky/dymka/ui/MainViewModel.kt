package com.ribsky.dymka.ui

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.play.core.splitinstall.SplitInstallStateUpdatedListener
import com.google.android.play.core.splitinstall.model.SplitInstallSessionStatus
import com.ribsky.billing.manager.SubManager
import com.ribsky.common.utils.dynamic.DynamicModule
import com.ribsky.core.Resource
import com.ribsky.core.mapper.ResultMapper.Companion.asResource
import com.ribsky.domain.model.user.BaseUserModel
import com.ribsky.domain.usecase.bio.IsNeedToFillBioUseCase
import com.ribsky.domain.usecase.config.GetDiscountUseCase
import com.ribsky.domain.usecase.sp.GetRateDialogStatusUseCase
import com.ribsky.domain.usecase.user.GetUserUseCase
import kotlinx.coroutines.launch

class MainViewModel(
    private val getUserUseCase: GetUserUseCase,
    private val subManager: SubManager,
    private val getRateDialogStatusUseCase: GetRateDialogStatusUseCase,
    private val dynamicModule: DynamicModule,
    private val getDiscountUseCase: GetDiscountUseCase,
    private val isNeedToFillBioUseCase: IsNeedToFillBioUseCase,
) : ViewModel() {

    private val _userStatus: MutableLiveData<Resource<BaseUserModel>> =
        MutableLiveData()
    val userStatus: LiveData<Resource<BaseUserModel>> get() = _userStatus

    private val _discountStatus = MutableLiveData<Resource<String>>()
    val discountStatus: LiveData<Resource<String>> = _discountStatus

    private val _dynamicModuleStatus: MutableLiveData<DynamicModule.State> =
        MutableLiveData(DynamicModule.State.NONE)
    val dynamicModuleStatus: LiveData<DynamicModule.State> get() = _dynamicModuleStatus

    val mDynamicModule = dynamicModule

    private val listener: SplitInstallStateUpdatedListener by lazy {
        SplitInstallStateUpdatedListener { state ->
            if (state.sessionId() == dynamicModule.sessionId) {
                when (state.status()) {
                    SplitInstallSessionStatus.DOWNLOADING ->
                        _dynamicModuleStatus.value = DynamicModule.State.DOWNLOADING

                    SplitInstallSessionStatus.INSTALLED -> {
                        dynamicModule.unregisterListener(listener)
                        _dynamicModuleStatus.value = DynamicModule.State.INSTALL_FINISHED
                    }

                    SplitInstallSessionStatus.REQUIRES_USER_CONFIRMATION -> _dynamicModuleStatus.value =
                        DynamicModule.State.REQUIRES_USER_CONFIRMATION(state)


                    SplitInstallSessionStatus.FAILED -> {
                        dynamicModule.unregisterListener(listener)
                        _dynamicModuleStatus.value = DynamicModule.State.FAILED(Exception())
                    }

                    SplitInstallSessionStatus.CANCELED -> {
                        dynamicModule.unregisterListener(listener)
                        _dynamicModuleStatus.value = DynamicModule.State.NONE
                    }

                    SplitInstallSessionStatus.CANCELING -> _dynamicModuleStatus.value =
                        DynamicModule.State.NONE


                    SplitInstallSessionStatus.DOWNLOADED -> {
                    }

                    SplitInstallSessionStatus.INSTALLING -> _dynamicModuleStatus.value =
                        DynamicModule.State.DOWNLOADING

                    SplitInstallSessionStatus.PENDING -> {
                    }

                    SplitInstallSessionStatus.UNKNOWN -> _dynamicModuleStatus.value =
                        DynamicModule.State.NONE

                }
            }
        }
    }

    val isShouldShowRateDialog: Boolean get() = getRateDialogStatusUseCase.isShouldShowRateDialog()

    val isSub get() = subManager.isSub()

    val isNeedToFillBio get() = isNeedToFillBioUseCase.invoke()

    fun getProfile() {
        viewModelScope.launch {
            _userStatus.value = Resource.loading()
            _userStatus.value = getUserUseCase.invoke().asResource()
        }
    }

    fun getDiscount() {
        viewModelScope.launch {
            _discountStatus.value = Resource.loading()
            val result = getDiscountUseCase.invoke().asResource()
            _discountStatus.value = result
        }
    }

    fun initDynamicModule(context: Context) {
        dynamicModule.initSplitInstallManager(context)
        if (dynamicModule.isModuleInstalled()) {
            _dynamicModuleStatus.value = DynamicModule.State.INSTALLED
        } else {
            dynamicModule.startInstallModule(
                listener,
                callbackOnError = {
                    _dynamicModuleStatus.value = DynamicModule.State.FAILED(it)
                },
                callbackOnSuccess = {
                    _dynamicModuleStatus.value = DynamicModule.State.DOWNLOADING
                }
            )
        }
    }
}
