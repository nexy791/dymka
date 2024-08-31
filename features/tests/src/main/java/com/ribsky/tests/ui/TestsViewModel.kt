package com.ribsky.tests.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ribsky.billing.manager.SubManager
import com.ribsky.common.base.BaseViewModel
import com.ribsky.core.Resource
import com.ribsky.domain.model.top.BaseTopModel
import com.ribsky.domain.usecase.config.GetDiscountUseCase
import com.ribsky.domain.usecase.file.IsContentExistsUseCase
import com.ribsky.domain.usecase.score.GetTestScoreUseCase
import com.ribsky.domain.usecase.streak.IsTodayStreakUseCase
import com.ribsky.domain.usecase.streak.SetTodayStreakUseCase
import com.ribsky.domain.usecase.test.TestInteractor
import com.ribsky.domain.usecase.top.TopInteractor
import com.ribsky.tests.model.TestModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class TestsViewModel(
    private val testInteractor: TestInteractor,
    private val subManager: SubManager,
    private val isContentExistsUseCase: IsContentExistsUseCase,
    private val setTodayStreakUseCase: SetTodayStreakUseCase,
    private val isTodayStreakUseCase: IsTodayStreakUseCase,
    private val getDiscountUseCase: GetDiscountUseCase,
    private val topInteractor: TopInteractor,
    private val getTestScoreUseCase: GetTestScoreUseCase,
) : BaseViewModel(subManager, getDiscountUseCase) {

    private val _testStatus: MutableLiveData<Resource<List<TestModel>>> =
        MutableLiveData()
    val testStatus: LiveData<Resource<List<TestModel>>> get() = _testStatus

    private val _scoreStatus: MutableLiveData<Resource<Int>> =
        MutableLiveData()
    val scoreStatus: LiveData<Resource<Int>> get() = _scoreStatus

    val isTodayStreak get() = isTodayStreakUseCase.invoke()

    init {
        getScore()
    }

    fun updateTodayStreak() {
        setTodayStreakUseCase.invoke()
    }

    fun isFileExists(content: String): Boolean {
        return if (content == ".") true
        else isContentExistsUseCase.invoke(content)
    }

    fun getScore() {
        viewModelScope.launch {
            _scoreStatus.value = Resource.loading()
            val score = getTestScoreUseCase.invoke()
            _scoreStatus.value = Resource.success(score)
        }
    }

    fun getTests() {
        viewModelScope.launch {
            _testStatus.value = Resource.loading()
            val list = testInteractor.getTests()
            val generatedList = list.map { test ->
                TestModel(test, isTestActive(test.hasPrem))
            }
            delay(500)
            _testStatus.value = Resource.success(generatedList.sortedBy { it.sort })
        }
    }

    private fun isTestActive(isTestActive: Boolean): Boolean {
        if (!isTestActive) return true
        return isSub
    }

    fun isNeedToShowWillUp(callback: (Result<List<BaseTopModel>>) -> Unit) {
        viewModelScope.launch {
            val result = topInteractor.getUsersForUpByTests()
            if (result.size >= 2) callback.invoke(Result.success(result))
            else callback.invoke(Result.failure(Throwable("Bad result")))
        }
    }

    fun isNeedToShowMore(callback: (Result<List<BaseTopModel>>) -> Unit) {
        viewModelScope.launch {
            val result = topInteractor.getUsersForMoreUpByTests()
            if (result.size >= 2) callback.invoke(Result.success(result))
            else callback.invoke(Result.failure(Throwable("Bad result")))
        }
    }

}
