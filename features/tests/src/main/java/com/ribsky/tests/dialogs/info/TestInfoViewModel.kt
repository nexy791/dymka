package com.ribsky.tests.dialogs.info

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ribsky.core.Resource
import com.ribsky.domain.model.test.BaseTestModel
import com.ribsky.domain.usecase.test.TestInteractor
import kotlinx.coroutines.launch

class TestInfoViewModel(
    private val testInteractor: TestInteractor,
) : ViewModel() {

    private val _testStatus: MutableLiveData<Resource<BaseTestModel>> = MutableLiveData()
    val testStatus: MutableLiveData<Resource<BaseTestModel>> get() = _testStatus

    fun getTest(id: String) {
        _testStatus.value = Resource.loading()
        viewModelScope.launch {
            _testStatus.value = Resource.success(testInteractor.getTest(id))
        }
    }
}
