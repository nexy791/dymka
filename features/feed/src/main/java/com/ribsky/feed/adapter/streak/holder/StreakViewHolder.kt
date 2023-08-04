package com.ribsky.feed.adapter.streak.holder

import android.graphics.drawable.AnimatedVectorDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ribsky.common.alias.commonDrawable
import com.ribsky.common.utils.ext.ViewExt.Companion.formatDays
import com.ribsky.feed.adapter.streak.StreakAdapter
import com.ribsky.feed.databinding.ItemStreakBinding

class StreakViewHolder(private val binding: ItemStreakBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: StreakAdapter.StreakModel, callback: StreakAdapter.OnClickListener) =
        with(binding) {
            (imageView3.drawable as AnimatedVectorDrawable).apply {
                start()
            }
            root.setOnClickListener {
                callback.onClickInfo()
            }

            tvDay.text = item.streak.formatDays()
            checkbox.setImageResource(if (item.isToday) commonDrawable.ic_round_check_circle_24 else commonDrawable.ic_round_check_circle_outline_24)

        }

    companion object {
        fun create(parent: ViewGroup): StreakViewHolder {
            val binding = ItemStreakBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            return StreakViewHolder(binding)
        }
    }
}
