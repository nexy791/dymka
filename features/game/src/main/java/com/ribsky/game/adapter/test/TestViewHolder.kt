package com.ribsky.game.adapter.test

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.text.parseAsHtml
import androidx.core.view.isGone
import androidx.recyclerview.widget.RecyclerView
import com.ribsky.common.utils.ext.ResourceExt.Companion.toColor
import com.ribsky.domain.model.word.BaseWordModel
import com.ribsky.game.databinding.ItemTestPickBinding

class TestViewHolder(private val binding: ItemTestPickBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(item: BaseWordModel.PickModel, onClickListener: TestAdapter.OnClickListener) =
        with(binding) {
            root.setCardBackgroundColor("#FFFFFF".toColor())
            root.setOnClickListener {
                onClickListener.onClick(item.correct)
                root.setCardBackgroundColor(
                    when (item.correct) {
                        true -> "#A5D6A7".toColor()
                        false -> "#EF9A9A".toColor()
                    }
                )
                tvIcon.isGone = !item.correct
            }
            root.setCardBackgroundColor("#FFFFFF".toColor())
            tvIcon.isGone = true
            tvTitle.text = item.name.parseAsHtml()
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
