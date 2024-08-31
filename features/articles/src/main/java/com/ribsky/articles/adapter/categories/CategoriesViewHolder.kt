package com.ribsky.articles.adapter.categories

import android.annotation.SuppressLint
import androidx.recyclerview.widget.RecyclerView
import com.ribsky.articles.databinding.ItemArticleCategoryBinding
import com.ribsky.articles.model.CategoryModel
import com.ribsky.common.alias.commonColor
import com.ribsky.common.utils.ext.ResourceExt.Companion.color
import com.ribsky.common.utils.ext.ResourceExt.Companion.toStateList

class CategoriesViewHolder(private val binding: ItemArticleCategoryBinding) :
    RecyclerView.ViewHolder(binding.root) {

    @SuppressLint("NotifyDataSetChanged")
    fun bind(
        item: CategoryModel,
        onClickListener: CategoriesAdapter.OnClickListener
    ) = with(binding) {
        root.setOnClickListener { onClickListener.onClick(item) }
        chipCategory.text = item.name

        if (item.selected) {
            chipCategory.apply {
                chipBackgroundColor = context.color(commonColor.blue).toStateList()
                setTextColor(context.color(commonColor.white))
                chipStrokeWidth = 0f
            }
        } else {
            chipCategory.apply {
                chipBackgroundColor = context.color(commonColor.white).toStateList()
                setTextColor(context.color(commonColor.black))
                chipStrokeWidth = 2f
            }
        }

    }
}
