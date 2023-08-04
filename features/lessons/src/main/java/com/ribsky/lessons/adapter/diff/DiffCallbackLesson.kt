package com.ribsky.lessons.adapter.diff

import androidx.recyclerview.widget.DiffUtil
import com.ribsky.domain.model.lesson.BaseLessonModel

object DiffCallbackLesson : DiffUtil.ItemCallback<BaseLessonModel>() {
    override fun areItemsTheSame(oldItem: BaseLessonModel, newItem: BaseLessonModel): Boolean =
        oldItem == newItem

    override fun areContentsTheSame(oldItem: BaseLessonModel, newItem: BaseLessonModel): Boolean =
        oldItem.isDone == newItem.isDone && oldItem.stars == newItem.stars
}
