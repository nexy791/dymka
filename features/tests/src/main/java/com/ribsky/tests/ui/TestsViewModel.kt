package com.ribsky.tests.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ribsky.billing.manager.SubManager
import com.ribsky.common.livedata.Resource
import com.ribsky.domain.usecase.file.IsContentExistsUseCase
import com.ribsky.domain.usecase.test.TestInteractor
import com.ribsky.tests.model.TestModel
import kotlinx.coroutines.launch

class TestsViewModel(
    private val testInteractor: TestInteractor,
    private val subManager: SubManager,
    private val isContentExistsUseCase: IsContentExistsUseCase,
) : ViewModel() {

    private val _testStatus: MutableLiveData<Resource<List<TestModel>>> =
        MutableLiveData()
    val testStatus: LiveData<Resource<List<TestModel>>> get() = _testStatus

    private val isSub get() = subManager.isSub()

    init {
        getTests()
    }

    fun isFileExists(content: String): Boolean {
        return if (content == ".") true
        else isContentExistsUseCase.invoke(content)
    }

    private fun getTests() {
        viewModelScope.launch {
            _testStatus.value = Resource.loading()
            val list = testInteractor.getTests()
            val generatedList = list.map { test ->
                TestModel(test, isTestActive(test.hasPrem))
            }
            _testStatus.value = Resource.success(generatedList.sortedBy { it.sort })
        }
    }

    private fun isTestActive(isTestActive: Boolean): Boolean {
        if (!isTestActive) return true
        return isSub
    }
}
