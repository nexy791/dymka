package com.ribsky.feed.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ribsky.billing.manager.SubManager
import com.ribsky.core.Resource
import com.ribsky.domain.model.best.BaseBestWordModel
import com.ribsky.domain.model.paragraph.BaseParagraphModel
import com.ribsky.domain.usecase.best.GetBestWordUseCase
import com.ribsky.domain.usecase.config.GetDiscountUseCase
import com.ribsky.domain.usecase.paragraph.ParagraphInteractor
import com.ribsky.domain.usecase.streak.GetCurrentStreakUseCase
import com.ribsky.domain.usecase.streak.IsTodayStreakUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class FeedViewModel(
    private val paragraphInteractor: ParagraphInteractor,
    private val getBestWordUseCase: GetBestWordUseCase,
    private val subManager: SubManager,
    private val isTodayStreakUseCase: IsTodayStreakUseCase,
    private val getCurrentStreakUseCase: GetCurrentStreakUseCase,
    private val getDiscountUseCase: GetDiscountUseCase,
) : ViewModel() {

    private val _paragraphsStatus: MutableLiveData<Resource<List<BaseParagraphModel>>> =
        MutableLiveData()
    val paragraphsStatus: LiveData<Resource<List<BaseParagraphModel>>> get() = _paragraphsStatus

    val paragraphs get() = _paragraphsStatus.value?.data

    private val _bestWordStatus: MutableLiveData<Resource<BaseBestWordModel>> =
        MutableLiveData()
    val bestWordStatus: LiveData<Resource<BaseBestWordModel>> get() = _bestWordStatus

    private val _discountStatus = MutableLiveData<String?>()
    val discount get() =  _discountStatus.value

    init {
        getIsFreeDiscountAvailable()
    }

    private fun getIsFreeDiscountAvailable() {
        viewModelScope.launch {

            if (subManager.isDiscount()) {
                _discountStatus.value = "Назавжди ∞"
                return@launch
            }

            val result = getDiscountUseCase.invoke()
            result.fold(
                onSuccess = {
                    _discountStatus.value = "до $it"
                },
                onFailure = {
                    _discountStatus.value = null
                }
            )
        }
    }

    fun getBestWord() {
        viewModelScope.launch {
            _bestWordStatus.value = Resource.loading()
            val word = getBestWordUseCase.getCurrentWord()
            delay(500)
            _bestWordStatus.value = Resource.success(word)
        }
    }

    fun getParagraphs() {
        viewModelScope.launch {
            _paragraphsStatus.value = Resource.loading()
            val paragraphs = paragraphInteractor.getParagraphs()
            val list = paragraphs.sortedBy { it.sort }
            _paragraphsStatus.value = Resource.success(list)
        }
    }

    val isTodayStreak get() = isTodayStreakUseCase.invoke()

    val currentStreak get() = getCurrentStreakUseCase.invoke()

    val isSub get() = subManager.isSub()
}
