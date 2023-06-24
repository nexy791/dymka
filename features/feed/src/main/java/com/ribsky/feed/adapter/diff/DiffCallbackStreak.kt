package com.ribsky.feed.adapter.diff

import androidx.recyclerview.widget.DiffUtil
import com.ribsky.feed.adapter.streak.StreakAdapter


object DiffCallbackStreak : DiffUtil.ItemCallback<StreakAdapter.StreakModel>() {
    override fun areItemsTheSame(
        oldItem: StreakAdapter.StreakModel,
        newItem: StreakAdapter.StreakModel,
    ): Boolean = oldItem.streak == newItem.streak && oldItem.isToday == newItem.isToday

    override fun areContentsTheSame(
        oldItem: StreakAdapter.StreakModel,
        newItem: StreakAdapter.StreakModel,
    ): Boolean =
        oldItem.streak == newItem.streak && oldItem.isToday == newItem.isToday
}
