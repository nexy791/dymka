package com.ribsky.dialogs.base

import androidx.recyclerview.widget.LinearLayoutManager
import com.ribsky.common.base.BaseSheet
import com.ribsky.dialogs.adapter.action.ListActionsAdapter
import com.ribsky.dialogs.databinding.DialogListBinding

class ListDialog(
    private val config: Config,
) : BaseSheet<DialogListBinding>(DialogListBinding::inflate) {

    data class Item(
        var text: String,
        var callback: (() -> Unit)? = null,
    )

    data class Config(
        var items: List<Item> = listOf(),
        var isCancelable: Boolean = true,
        var onDismiss: (() -> Unit)? = null,
    )

    override fun initViews(): Unit = with(binding) {
        recycler.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = ListActionsAdapter().apply {
                submitList(config.items)
            }
        }
    }

    override fun initObserves() {
    }

    override fun clear() {
    }

    companion object {
        inline fun create(
            block: Config.() -> Unit,
        ): ListDialog {
            val config = Config().apply(block)
            return ListDialog(config)
        }
    }
}
