package com.ribsky.article.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ribsky.core.Resource
import com.ribsky.core.mapper.ResultMapper.Companion.asResource
import com.ribsky.domain.model.article.BaseArticleModel
import com.ribsky.domain.model.article.slider.BaseSliderModel
import com.ribsky.domain.usecase.article.GetArticleContentUseCase
import com.ribsky.domain.usecase.article.GetArticleUseCase
import kotlinx.coroutines.launch

class ArticleViewModel(
    private val getArticleContentUseCase: GetArticleContentUseCase,
    private val getArticleUseCase: GetArticleUseCase
) : ViewModel() {

    private val _articleStatus: MutableLiveData<Resource<BaseArticleModel>> = MutableLiveData()
    val articleStatus: LiveData<Resource<BaseArticleModel>> = _articleStatus

    private val _contentStatus: MutableLiveData<Resource<BaseSliderModel>> = MutableLiveData()
    val contentStatus: LiveData<Resource<BaseSliderModel>> = _contentStatus

    fun getArticle(articleId: String) {
        viewModelScope.launch {
            _articleStatus.value = Resource.loading()
            val article = getArticleUseCase.invoke(articleId)
            _articleStatus.value = Resource.success(article)
        }
    }

    fun getContent(content: String) {
        viewModelScope.launch {
            _contentStatus.value = getArticleContentUseCase.invoke(content).asResource()
        }
    }
}