package com.ribsky.paywall.dialogs

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ribsky.core.Resource
import com.ribsky.domain.model.top.BaseTopModel
import com.ribsky.domain.usecase.top.TopInteractor
import com.ribsky.paywall.model.CatModel
import kotlinx.coroutines.launch

class PayWallViewModel(
    private val topInteractor: TopInteractor,
) : ViewModel() {

    private val _topStatus = MutableLiveData<Resource<List<BaseTopModel>>>()
    val topStatus: LiveData<Resource<List<BaseTopModel>>> = _topStatus

    fun getTop() {
        viewModelScope.launch {
            _topStatus.value = Resource.loading()
            val result = topInteractor.loadUsersByPremium()
            _topStatus.value = Resource.success(result.take(15))
        }
    }

}