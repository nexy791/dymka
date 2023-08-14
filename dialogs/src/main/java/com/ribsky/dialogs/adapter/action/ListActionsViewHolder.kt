package com.ribsky.dialogs.adapter.action

import androidx.recyclerview.widget.RecyclerView
import com.ribsky.dialogs.base.ListDialog
import com.ribsky.dialogs.databinding.ItemDialogListBinding

class ListActionsViewHolder(val binding: ItemDialogListBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: ListDialog.Item, callback: () -> Unit) = with(binding) {
        tvTitle.text = item.text
        root.setOnClickListener {
            item.callback?.invoke()
            callback.invoke()
        }
    }
}
