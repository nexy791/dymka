package com.ribsky.shop.adapter.cats

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.ribsky.domain.model.top.BaseTopModel
import com.ribsky.shop.adapter.cats.diff.CatsDiffCallback
import com.ribsky.shop.adapter.cats.CatsViewHolder

class CatsAdapter(
    private val onCatClick: (BaseTopModel) -> Unit
) : ListAdapter<BaseTopModel, CatsViewHolder>(CatsDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatsViewHolder =
        CatsViewHolder.create(parent)

    override fun onBindViewHolder(holder: CatsViewHolder, position: Int) {
        holder.bind(getItem(position), onCatClick)
    }
}