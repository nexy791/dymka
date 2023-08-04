package com.ribsky.feed.adapter.streak

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.ribsky.feed.adapter.diff.DiffCallbackStreak
import com.ribsky.feed.adapter.streak.holder.StreakViewHolder

class StreakAdapter(private val onClickListener: OnClickListener) :
    ListAdapter<StreakAdapter.StreakModel, StreakViewHolder>(DiffCallbackStreak) {

    data class StreakModel(
        val streak: Int,
        val isToday: Boolean,
    )

    fun interface OnClickListener {
        fun onClickInfo()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StreakViewHolder =
        StreakViewHolder.create(parent)

    override fun onBindViewHolder(holder: StreakViewHolder, position: Int) {
        holder.bind(getItem(position), onClickListener)
    }
}
