package com.ribsky.lessons.adapter.lessons.header.holder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.google.firebase.storage.FirebaseStorage
import com.ribsky.domain.model.paragraph.BaseParagraphModel
import com.ribsky.lessons.adapter.lessons.header.LessonsHeaderAdapter
import com.ribsky.lessons.databinding.ItemLessonHeaderBinding
import org.koin.java.KoinJavaComponent.inject

class HeaderViewHolder(private val binding: ItemLessonHeaderBinding) :
    RecyclerView.ViewHolder(binding.root) {

    private val storage: FirebaseStorage by inject(FirebaseStorage::class.java)
    fun bind(
        item: BaseParagraphModel,
        onClickListener: LessonsHeaderAdapter.OnClickListener,
    ) = with(binding) {
        root.setOnClickListener {
            onClickListener.onClick()
        }
        circularProgressBar.setProgressWithAnimation(item.percent)
        tvTitle.text = item.name
        tvDescription.text = item.description
        imageView.load(storage.getReferenceFromUrl(item.image))
    }

    companion object {
        fun create(parent: ViewGroup): HeaderViewHolder {
            val binding = ItemLessonHeaderBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            return HeaderViewHolder(binding)
        }
    }
}
