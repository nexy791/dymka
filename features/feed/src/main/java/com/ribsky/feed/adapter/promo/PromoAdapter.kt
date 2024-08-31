package com.ribsky.feed.adapter.promo

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.ribsky.domain.model.promo.BasePromoModel
import com.ribsky.feed.adapter.diff.DiffCallbackPromo
import com.ribsky.feed.adapter.promo.holder.PromoViewHolder

class PromoAdapter(private val onClickListener: OnClickListener) :
    ListAdapter<BasePromoModel, PromoViewHolder>(DiffCallbackPromo) {

     fun interface OnClickListener {
        fun onClick(value: String)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PromoViewHolder =
        PromoViewHolder.create(parent)

    override fun onBindViewHolder(holder: PromoViewHolder, position: Int) {
        holder.bind(getItem(position), onClickListener)
    }
}
