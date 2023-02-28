package com.ribsky.feed.adapter.word

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.ribsky.domain.model.best.BaseBestWordModel
import com.ribsky.feed.adapter.diff.DiffCallbackBestWord
import com.ribsky.feed.adapter.word.holder.BestWordViewHolder

class BestWordAdapter(private val onClickListener: OnClickListener) :
    ListAdapter<BaseBestWordModel, BestWordViewHolder>(DiffCallbackBestWord) {

    fun interface OnClickListener {
        fun onClick(id: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BestWordViewHolder =
        BestWordViewHolder.create(parent)

    override fun onBindViewHolder(holder: BestWordViewHolder, position: Int) {
        holder.bind(getItem(position), onClickListener)
    }
}
