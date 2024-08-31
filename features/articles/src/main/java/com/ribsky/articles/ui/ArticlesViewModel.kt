package com.ribsky.articles.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ribsky.articles.model.CategoryModel
import com.ribsky.billing.manager.SubManager
import com.ribsky.common.base.BaseViewModel
import com.ribsky.core.Resource
import com.ribsky.domain.model.article.BaseArticleModel
import com.ribsky.domain.usecase.article.GetArticlesUseCase
import com.ribsky.domain.usecase.config.GetDiscountUseCase
import com.ribsky.domain.usecase.file.IsContentExistsUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ArticlesViewModel(
    private val subManager: SubManager,
    private val getDiscountUseCase: GetDiscountUseCase,
    private val isContentExistsUseCase: IsContentExistsUseCase,
    private val getArticlesUseCase: GetArticlesUseCase,
) : BaseViewModel(subManager, getDiscountUseCase) {

    private val _categories: MutableLiveData<Resource<List<CategoryModel>>> = MutableLiveData()
    val categories get() = _categories

    private val _articles: MutableLiveData<Resource<List<BaseArticleModel>>> = MutableLiveData()
    val articles get() = _articles

    private var articlesList: List<BaseArticleModel> = emptyList()
    private var categoriesList: List<CategoryModel> = emptyList()



    fun getArticles() {
        viewModelScope.launch {
            _articles.value = Resource.loading()
            val articles = getArticlesUseCase.invoke().sortedByDescending { it.sort }
            delay(500)
            getCategories(articles)
            _articles.value = Resource.success(articles)
            articlesList = articles
        }
    }

    private fun getCategories(articles: List<BaseArticleModel>) {
        viewModelScope.launch {
            _categories.value = Resource.loading()
            val categories = CategoryModel.default() + articles.map { it.categories }
                .flatten()
                .groupingBy { it }
                .eachCount()
                .toList()
                .sortedByDescending { it.second }
                .map { CategoryModel(it.first, CategoryModel.Type.OTHER) }

            _categories.value = Resource.success(categories)
            categoriesList = categories
        }
    }




    fun isFileExists(content: String) = isContentExistsUseCase.invoke(content)
    fun getArticlesByCategory(category: CategoryModel) {
        val newCategories = categoriesList.map { it.copy(selected = it.name == category.name) }
        _categories.value = Resource.success(newCategories)

        when (category.type) {
            CategoryModel.Type.ALL -> _articles.value = Resource.success(articlesList)
            CategoryModel.Type.SAVED -> {

            }
            CategoryModel.Type.OTHER -> {
                val filteredArticles = articlesList.filter { article -> article.categories.any { it in category.name } }
                _articles.value = Resource.success(filteredArticles)
            }
        }
    }
}