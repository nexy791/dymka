package com.ribsky.lessons.adapter.lessons.item.holder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.ribsky.common.alias.commonDrawable
import com.ribsky.common.utils.ext.ViewExt.Companion.getInitials
import com.ribsky.domain.model.lesson.BaseLessonModel
import com.ribsky.lessons.adapter.lessons.item.LessonsAdapter
import com.ribsky.lessons.databinding.ItemLessonBinding

class LessonViewHolder(private val binding: ItemLessonBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(item: BaseLessonModel, onClickListener: LessonsAdapter.OnClickListener) =
        with(binding) {
            root.setOnClickListener {
                onClickListener.onClick(item)
            }

            imageView.avatarInitials = getInitials(item.name)

            tvTitle.text = item.name
            tvDescription.text = item.description

            btnChecked.updateState(item.isDone)

            btnPrem.isGone = !item.hasPrem
            btnDev.isGone = !item.isInProgress()

            ratingBar.apply {
                setRating(item.stars.toFloat())
                isGone = item.isInProgress()
            }
        }

    private fun MaterialButton.updateState(isDone: Boolean) {
        if (isDone) {
            text = "Пройдено"
            setIconResource(commonDrawable.ic_round_check_circle_outline_24)
        } else {
            text = "Не пройдено"
            icon = null
        }
    }

    companion object {
        fun create(parent: ViewGroup): LessonViewHolder {
            val binding = ItemLessonBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            return LessonViewHolder(binding)
        }
    }
}
