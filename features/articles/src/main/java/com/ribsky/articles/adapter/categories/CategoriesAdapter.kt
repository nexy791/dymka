package com.ribsky.articles.adapter.categories

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.ribsky.articles.adapter.diff.DiffCallbackCategories
import com.ribsky.articles.databinding.ItemArticleBinding
import com.ribsky.articles.databinding.ItemArticleCategoryBinding
import com.ribsky.articles.model.CategoryModel

class CategoriesAdapter(private val onClickListener: OnClickListener) :
    ListAdapter<CategoryModel, CategoriesViewHolder>(DiffCallbackCategories) {


    fun interface OnClickListener {
        fun onClick(category: CategoryModel)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriesViewHolder =
        CategoriesViewHolder(
            ItemArticleCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(holder: CategoriesViewHolder, position: Int) {
        holder.bind(getItem(position), onClickListener)
    }
}
