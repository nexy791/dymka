package com.ribsky.articles.adapter.diff

import androidx.recyclerview.widget.DiffUtil
import com.ribsky.domain.model.article.ArticleModel
import com.ribsky.domain.model.article.BaseArticleModel

object DiffCallbackArticles : DiffUtil.ItemCallback<BaseArticleModel>() {
    override fun areItemsTheSame(
        oldItem: BaseArticleModel,
        newItem: BaseArticleModel,
    ): Boolean =
        oldItem == newItem

    override fun areContentsTheSame(
        oldItem: BaseArticleModel,
        newItem: BaseArticleModel,
    ): Boolean =
        oldItem.name == newItem.name
}
