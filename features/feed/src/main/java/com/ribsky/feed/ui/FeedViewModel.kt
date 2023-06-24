package com.ribsky.feed.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ribsky.billing.manager.SubManager
import com.ribsky.common.livedata.Resource
import com.ribsky.domain.model.best.BaseBestWordModel
import com.ribsky.domain.model.paragraph.BaseParagraphModel
import com.ribsky.domain.usecase.best.GetBestWordUseCase
import com.ribsky.domain.usecase.paragraph.ParagraphInteractor
import com.ribsky.domain.usecase.streak.GetCurrentStreakUseCase
import com.ribsky.domain.usecase.streak.IsTodayStreakUseCase
import kotlinx.coroutines.launch

class FeedViewModel(
    private val paragraphInteractor: ParagraphInteractor,
    private val getBestWordUseCase: GetBestWordUseCase,
    private val subManager: SubManager,
    private val isTodayStreakUseCase: IsTodayStreakUseCase,
    private val getCurrentStreakUseCase: GetCurrentStreakUseCase,
) : ViewModel() {

    private val _lessonsStatus: MutableLiveData<Resource<List<BaseParagraphModel>>> =
        MutableLiveData()
    val lessonsStatus: LiveData<Resource<List<BaseParagraphModel>>> get() = _lessonsStatus

    private val _bestWordStatus: MutableLiveData<Resource<BaseBestWordModel>> =
        MutableLiveData()
    val bestWordStatus: LiveData<Resource<BaseBestWordModel>> get() = _bestWordStatus

    fun getBestWord() {
        viewModelScope.launch {
            _bestWordStatus.value = Resource.loading()
            val word = getBestWordUseCase.getCurrentWord()
            _bestWordStatus.value = Resource.success(word)
        }
    }

    fun getParagraphs() {
        viewModelScope.launch {
            _lessonsStatus.value = Resource.loading()
            val paragraphs = paragraphInteractor.getParagraphs()
            val list = paragraphs.sortedBy { it.sort }
            _lessonsStatus.value = Resource.success(list)
        }
    }

    val isTodayStreak get() = isTodayStreakUseCase.invoke()

    val currentStreak get() = getCurrentStreakUseCase.invoke()

    val isSub get() = subManager.isSub()
}
