package com.ribsky.articles.adapter.articles

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.ribsky.articles.adapter.diff.DiffCallbackArticles
import com.ribsky.articles.databinding.ItemArticleBinding
import com.ribsky.domain.model.article.ArticleModel
import com.ribsky.domain.model.article.BaseArticleModel

class ArticlesAdapter(private val onClickListener: OnClickListener) :
    ListAdapter<BaseArticleModel, ArticlesViewHolder>(DiffCallbackArticles) {


    fun interface OnClickListener {
        fun onClick(article: BaseArticleModel)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticlesViewHolder =
        ArticlesViewHolder(
            ItemArticleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(holder: ArticlesViewHolder, position: Int) {
        holder.bind(getItem(position), onClickListener)
    }
}
