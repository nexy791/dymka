package com.ribsky.lesson.adapter.chat

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.ribsky.lesson.adapter.chat.factory.type.TypeFactory
import com.ribsky.lesson.adapter.chat.factory.type.TypeFactoryImpl
import com.ribsky.lesson.adapter.chat.factory.viewholder.ViewHolderFactory
import com.ribsky.lesson.adapter.chat.factory.viewholder.ViewHolderFactoryImpl
import com.ribsky.lesson.adapter.chat.holder.base.BaseViewHolder
import com.ribsky.lesson.adapter.diff.DiffCallbackChat
import com.ribsky.lesson.model.BaseActiveItem
import com.ribsky.lesson.model.ChatModel

class ChatAdapter(
    private val photo: String,
    private val callback: OnChatClickListener,
) : ListAdapter<ChatModel, BaseViewHolder<ChatModel>>(DiffCallbackChat) {

    private val viewFactory: ViewHolderFactory = ViewHolderFactoryImpl()
    private val typeFactory: TypeFactory = TypeFactoryImpl()

    interface OnChatClickListener {
        fun onTextClick(text: String)
        fun onMistakeClick(text: String)
        fun onTestClick(test: ChatModel.Test.TestModel)
        fun onChipsClick(chips: List<String>)
    }

    fun disableActiveLastElement() {
        val position = currentList.indexOfLast { (it is BaseActiveItem) }
        if (position == -1) return
        (getItem(position) as BaseActiveItem).apply {
            isActive = false
            notifyItemChanged(position)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<ChatModel> =
        viewFactory.createViewHolder(parent, viewType)

    override fun onBindViewHolder(holder: BaseViewHolder<ChatModel>, position: Int) = with(holder) {
        setUserPhoto(photo)
        bind(getItem(position), callback)
    }

    override fun getItemViewType(position: Int): Int = getItem(position).type(typeFactory)
}
