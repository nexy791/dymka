package com.ribsky.bot.adapter.reply

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.ribsky.bot.adapter.reply.diff.DiffCallbackReply

class ReplyAdapter(
    private val callback: OnReplyClickListener,
) : ListAdapter<String, ReplyViewHolder>(DiffCallbackReply) {

    interface OnReplyClickListener {
        fun onReplyClick(text: String)
        fun onLimitClick()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReplyViewHolder =
        ReplyViewHolder.create(parent)

    override fun onBindViewHolder(holder: ReplyViewHolder, position: Int) {
        holder.bind(getItem(position), callback)
    }

    fun setScore(it: Int) {
        val default = listOf("\uD83D\uDE3A $it відповідей")
        val list = currentList.toMutableList()
        submitList(
            default + list.apply {
                if (size > 0) removeAt(0)
            }
        )
    }
}
