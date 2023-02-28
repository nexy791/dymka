package com.ribsky.games.adapter.diff

import androidx.recyclerview.widget.DiffUtil
import com.ribsky.games.model.GameModel

object DiffCallbackGame : DiffUtil.ItemCallback<GameModel>() {
    override fun areItemsTheSame(
        oldItem: GameModel,
        newItem: GameModel,
    ): Boolean =
        oldItem == newItem

    override fun areContentsTheSame(
        oldItem: GameModel,
        newItem: GameModel,
    ): Boolean =
        oldItem.isPicked == newItem.isPicked
}
