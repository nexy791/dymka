package com.ribsky.dialogs.factory.message

import com.ribsky.dialogs.base.DialogFactory
import com.ribsky.dialogs.base.ListDialog

class MessageActionFactory(
    private val items: List<ListDialog.Item>,
) : DialogFactory {

    override fun createDialog(): ListDialog = ListDialog.create {
        items = this@MessageActionFactory.items
    }
}
