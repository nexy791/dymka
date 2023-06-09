package com.ribsky.dialogs.adapter.action

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.ribsky.dialogs.base.ListDialog
import com.ribsky.dialogs.databinding.ItemDialogListBinding

class ListActionsAdapter : ListAdapter<ListDialog.Item, ListActionsViewHolder>(DiffUtilsActions) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListActionsViewHolder =
        ListActionsViewHolder(
            ItemDialogListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ListActionsViewHolder, position: Int) =
        holder.bind(getItem(position))
}
