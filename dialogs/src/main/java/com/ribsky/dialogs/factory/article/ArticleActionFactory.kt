package com.ribsky.dialogs.factory.article

import com.ribsky.dialogs.base.DialogFactory
import com.ribsky.dialogs.base.ListDialog

class ArticleActionFactory(
    private val items: List<ListDialog.Item>,
) : DialogFactory {

    override fun createDialog(): ListDialog = ListDialog.create {
        items = this@ArticleActionFactory.items
    }
}
