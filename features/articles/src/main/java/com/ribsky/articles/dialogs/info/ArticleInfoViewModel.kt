package com.ribsky.articles.dialogs.info

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ribsky.core.Resource
import com.ribsky.domain.model.article.BaseArticleModel
import com.ribsky.domain.usecase.article.GetArticleUseCase
import kotlinx.coroutines.launch

class ArticleInfoViewModel(
    private val getArticleUseCase: GetArticleUseCase,
) : ViewModel() {

    private val _articleStatus: MutableLiveData<Resource<BaseArticleModel>> = MutableLiveData()
    val articleStatus: LiveData<Resource<BaseArticleModel>> get() = _articleStatus
    fun getLesson(lessonId: String) {
        viewModelScope.launch {
            _articleStatus.value = Resource.loading()
            _articleStatus.value = Resource.success(getArticleUseCase.invoke(lessonId))
        }
    }
}
