package com.ribsky.lessons.adapter.stars

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.ribsky.lessons.adapter.diff.DiffCallbackStar
import com.ribsky.lessons.adapter.stars.holder.StarViewHolder
import com.ribsky.lessons.model.StarModel

class StarAdapter(private val onClickListener: OnClickListener) :
    ListAdapter<StarModel, StarViewHolder>(DiffCallbackStar) {

    fun interface OnClickListener {
        fun onClick(stars: StarModel)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StarViewHolder =
        StarViewHolder.create(parent)

    override fun onBindViewHolder(holder: StarViewHolder, position: Int) {
        holder.bind(getItem(position), onClickListener)
    }
}
