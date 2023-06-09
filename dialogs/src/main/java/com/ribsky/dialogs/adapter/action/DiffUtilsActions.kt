package com.ribsky.dialogs.adapter.action

import androidx.recyclerview.widget.DiffUtil
import com.ribsky.dialogs.base.ListDialog

object DiffUtilsActions : DiffUtil.ItemCallback<ListDialog.Item>() {
    override fun areItemsTheSame(oldItem: ListDialog.Item, newItem: ListDialog.Item): Boolean =
        oldItem == newItem

    override fun areContentsTheSame(oldItem: ListDialog.Item, newItem: ListDialog.Item): Boolean =
        oldItem == newItem
}
