package com.ribsky.game.adapter.diff

import androidx.recyclerview.widget.DiffUtil
import com.ribsky.domain.model.word.BaseWordModel

object DiffCallbackTest : DiffUtil.ItemCallback<BaseWordModel.PickModel>() {
    override fun areItemsTheSame(
        oldItem: BaseWordModel.PickModel,
        newItem: BaseWordModel.PickModel,
    ): Boolean = oldItem == newItem

    override fun areContentsTheSame(
        oldItem: BaseWordModel.PickModel,
        newItem: BaseWordModel.PickModel,
    ): Boolean = oldItem.isShown == newItem.isShown
}
