package com.ribsky.feed.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ribsky.billing.manager.SubManager
import com.ribsky.common.base.BaseViewModel
import com.ribsky.core.Resource
import com.ribsky.core.mapper.ResultMapper.Companion.asResource
import com.ribsky.domain.model.best.BaseBestWordModel
import com.ribsky.domain.model.paragraph.BaseParagraphModel
import com.ribsky.domain.model.promo.BasePromoModel
import com.ribsky.domain.usecase.best.GetBestWordUseCase
import com.ribsky.domain.usecase.config.GetDiscountUseCase
import com.ribsky.domain.usecase.config.GetPromoUseCase
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
    private val getPromoUseCase: GetPromoUseCase,
) : BaseViewModel(subManager, getDiscountUseCase) {

    private val _paragraphsStatus: MutableLiveData<Resource<List<BaseParagraphModel>>> =
        MutableLiveData()
    val paragraphsStatus: LiveData<Resource<List<BaseParagraphModel>>> get() = _paragraphsStatus

    private val _bestWordStatus: MutableLiveData<Resource<BaseBestWordModel>> =
        MutableLiveData()
    val bestWordStatus: LiveData<Resource<BaseBestWordModel>> get() = _bestWordStatus

    private val _promoStatus = MutableLiveData<Resource<BasePromoModel>>()
    val promoStatus: LiveData<Resource<BasePromoModel>> get() = _promoStatus

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

    fun getPromo() {
        viewModelScope.launch {
            _promoStatus.value = Resource.loading()
            _promoStatus.value = getPromoUseCase.invoke().asResource()
        }
    }

    val isTodayStreak get() = isTodayStreakUseCase.invoke()

    val currentStreak get() = getCurrentStreakUseCase.invoke()

}
