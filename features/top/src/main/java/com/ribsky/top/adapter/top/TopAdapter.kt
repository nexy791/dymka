package com.ribsky.top.adapter.top

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ribsky.top.adapter.diff.DiffCallbackTop
import com.ribsky.top.adapter.top.holder.TopLessonViewHolder
import com.ribsky.top.adapter.top.holder.TopStreakViewHolder
import com.ribsky.top.adapter.top.holder.TopTestViewHolder
import com.ribsky.top.model.TopModel

class TopAdapter(
    private val onClickListener: OnClickListener,
) :
    ListAdapter<TopModel, RecyclerView.ViewHolder>(DiffCallbackTop) {

    fun interface OnClickListener {
        fun onClick(id: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        when (viewType) {
            TopModel.ViewType.LESSON.type -> TopLessonViewHolder.create(parent)
            TopModel.ViewType.TEST.type -> TopTestViewHolder.create(parent)
            TopModel.ViewType.STREAK.type -> TopStreakViewHolder.create(parent)
            else -> throw IllegalArgumentException("Unknown viewType $viewType")
        }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        when (holder) {
            is TopLessonViewHolder -> holder.bind(item, onClickListener)
            is TopTestViewHolder -> holder.bind(item, onClickListener)
            is TopStreakViewHolder -> holder.bind(item, onClickListener)
            else -> throw IllegalArgumentException("Unknown viewType $holder")
        }
    }

    override fun getItemViewType(position: Int): Int = getItem(position).type.type
}
