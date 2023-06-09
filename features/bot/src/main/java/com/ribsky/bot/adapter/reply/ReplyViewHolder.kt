package com.ribsky.bot.adapter.reply

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.text.parseAsHtml
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.ribsky.bot.databinding.ItemReplyBinding

class ReplyViewHolder(private val binding: ItemReplyBinding) :
    ViewHolder(binding.root) {

    fun bind(
        item: String,
        callback: ReplyAdapter.OnReplyClickListener,
    ): Unit = with(binding) {
        tvReply.text = item.parseAsHtml()
        root.setOnClickListener {
            if (bindingAdapterPosition != 0) callback.onReplyClick(item)
            else callback.onLimitClick()
        }
    }

    companion object {
        fun create(parent: ViewGroup): ReplyViewHolder {
            val binding = ItemReplyBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            return ReplyViewHolder(binding)
        }
    }
}
