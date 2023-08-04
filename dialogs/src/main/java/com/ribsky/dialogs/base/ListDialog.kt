package com.ribsky.dialogs.base

import android.os.Parcelable
import androidx.recyclerview.widget.LinearLayoutManager
import com.ribsky.common.base.BaseSheet
import com.ribsky.dialogs.adapter.action.ListActionsAdapter
import com.ribsky.dialogs.databinding.DialogListBinding
import kotlinx.parcelize.Parcelize

class ListDialog(
    private val config: Config? = null,
) : BaseSheet<DialogListBinding>(DialogListBinding::inflate) {

    @Parcelize
    data class Item(
        var text: String,
        var callback: (() -> Unit)? = null,
    ) : Parcelable

    @Parcelize
    data class Config(
        var items: List<Item> = listOf(),
        var isCancelable: Boolean = true,
        var onDismiss: (() -> Unit)? = null,
    ) : Parcelable

    override fun initViews(): Unit = with(binding) {
        if(config == null) dismiss()
        recycler.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = ListActionsAdapter().apply {
                submitList(config?.items)
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
