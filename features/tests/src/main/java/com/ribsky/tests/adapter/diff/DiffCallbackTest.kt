package com.ribsky.tests.adapter.diff

import androidx.recyclerview.widget.DiffUtil
import com.ribsky.tests.model.TestModel

object DiffCallbackTest : DiffUtil.ItemCallback<TestModel>() {
    override fun areItemsTheSame(oldItem: TestModel, newItem: TestModel): Boolean =
        oldItem == newItem

    override fun areContentsTheSame(oldItem: TestModel, newItem: TestModel): Boolean =
        oldItem.id == newItem.id
}
