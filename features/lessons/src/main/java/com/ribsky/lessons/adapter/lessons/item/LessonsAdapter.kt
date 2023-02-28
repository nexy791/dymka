package com.ribsky.lessons.adapter.lessons.item

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.ribsky.domain.model.lesson.BaseLessonModel
import com.ribsky.lessons.adapter.diff.DiffCallbackLesson
import com.ribsky.lessons.adapter.lessons.item.holder.LessonViewHolder

class LessonsAdapter(private val onClickListener: OnClickListener) :
    ListAdapter<BaseLessonModel, LessonViewHolder>(DiffCallbackLesson) {

    fun interface OnClickListener {
        fun onClick(lessonModel: BaseLessonModel)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LessonViewHolder =
        LessonViewHolder.create(parent)

    override fun onBindViewHolder(holder: LessonViewHolder, position: Int) {
        holder.bind(getItem(position), onClickListener)
    }
}
