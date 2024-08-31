package com.ribsky.articles.adapter.diff

import androidx.recyclerview.widget.DiffUtil
import com.ribsky.articles.model.CategoryModel
import com.ribsky.domain.model.article.ArticleModel

object DiffCallbackCategories : DiffUtil.ItemCallback<CategoryModel>() {
    override fun areItemsTheSame(
        oldItem: CategoryModel,
        newItem: CategoryModel,
    ): Boolean =
        oldItem == newItem

    override fun areContentsTheSame(
        oldItem: CategoryModel,
        newItem: CategoryModel,
    ): Boolean =
        oldItem.selected == newItem.selected
}
