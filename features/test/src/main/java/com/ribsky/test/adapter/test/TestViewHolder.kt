package com.ribsky.test.adapter.test

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.text.parseAsHtml
import androidx.core.view.isInvisible
import androidx.recyclerview.widget.RecyclerView
import com.ribsky.common.alias.commonColor
import com.ribsky.common.utils.ext.ResourceExt.Companion.color
import com.ribsky.common.utils.ext.ResourceExt.Companion.toColor
import com.ribsky.domain.model.word.BaseWordModel
import com.ribsky.test.databinding.ItemTestPickBinding

class TestViewHolder(private val binding: ItemTestPickBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(item: BaseWordModel.PickModel, onClickListener: TestAdapter.OnClickListener) =
        with(binding) {
            root.setCardBackgroundColor(root.context.color(commonColor.white))
            root.setOnClickListener {
                root.setCardBackgroundColor(
                    when (item.correct) {
                        true -> "#A5D6A7".toColor()
                        false -> "#EF9A9A".toColor()
                    }

                )
                onClickListener.onClick(item.correct, bindingAdapterPosition)
                tvIcon.isInvisible = false
            }
            tvTitle.text = item.name.parseAsHtml()
            tvIcon.isInvisible = !item.isShown
            tvIcon.text = if (item.correct) "✅" else "❌"
        }

    companion object {
        fun create(parent: ViewGroup): TestViewHolder {
            val binding = ItemTestPickBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            return TestViewHolder(binding)
        }
    }
}
