package com.ribsky.tests.adapter.diff

import androidx.recyclerview.widget.DiffUtil
import com.ribsky.tests.model.TestModel

object DiffCallbackTestHeader : DiffUtil.ItemCallback<Int>() {
    override fun areItemsTheSame(oldItem: Int, newItem: Int): Boolean =
        oldItem == newItem

    override fun areContentsTheSame(oldItem: Int, newItem: Int): Boolean =
        oldItem == newItem
}
