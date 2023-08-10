package com.ribsky.tests.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ribsky.billing.manager.SubManager
import com.ribsky.core.Resource
import com.ribsky.domain.model.top.BaseTopModel
import com.ribsky.domain.usecase.config.GetDiscountUseCase
import com.ribsky.domain.usecase.file.IsContentExistsUseCase
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
) : ViewModel() {

    private val _testStatus: MutableLiveData<Resource<List<TestModel>>> =
        MutableLiveData()
    val testStatus: LiveData<Resource<List<TestModel>>> get() = _testStatus

    private val isSub get() = subManager.isSub()
    val isTodayStreak get() = isTodayStreakUseCase.invoke()

    fun updateTodayStreak() {
        setTodayStreakUseCase.invoke()
    }

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
            delay(500)
            _testStatus.value = Resource.success(generatedList.sortedBy { it.sort })
        }
    }

    private fun isTestActive(isTestActive: Boolean): Boolean {
        if (!isTestActive) return true
        return isSub
    }

    // TODO: refactor this
    fun isNeedToShowPayWall(callback: (Result<String>) -> Unit) {
        viewModelScope.launch {
            val random = (0..1).random()
            if (random == 0) {
                callback.invoke(Result.failure(Throwable("Bad random")))
                return@launch
            }
            if (subManager.isSub()) {
                callback.invoke(Result.failure(Throwable("Bad Sub")))
                return@launch
            }

            val isLifeTimeDiscount = subManager.isDiscount()
            val isDiscountAvailable = getDiscountUseCase.invoke()

            if (isLifeTimeDiscount) {
                callback.invoke(Result.success("Назавжди ∞"))
                return@launch
            } else if (isDiscountAvailable.isSuccess) {
                callback.invoke(Result.success("до ${isDiscountAvailable.getOrNull()}"))
                return@launch
            } else {
                callback.invoke(Result.failure(Throwable(isDiscountAvailable.exceptionOrNull()?.message)))
                return@launch
            }

        }
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
