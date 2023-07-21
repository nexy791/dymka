package com.ribsky.share.ui.word

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ribsky.core.Resource
import com.ribsky.domain.model.best.BaseBestWordModel
import com.ribsky.domain.usecase.best.GetBestWordUseCase
import kotlinx.coroutines.launch

class ShareWordViewModel(
    private val bestWordUseCase: GetBestWordUseCase,
) : ViewModel() {

    private val _getBestWord: MutableLiveData<Resource<BaseBestWordModel>> = MutableLiveData()
    val getBestWord: MutableLiveData<Resource<BaseBestWordModel>> = _getBestWord

    fun getBestWord(id: Int) {
        _getBestWord.value = Resource.loading()
        viewModelScope.launch {
            val word = bestWordUseCase.getBestWordById(id)
            _getBestWord.value = Resource.success(word)
        }
    }
}
