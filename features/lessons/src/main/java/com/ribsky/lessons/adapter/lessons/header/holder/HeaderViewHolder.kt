package com.ribsky.lessons.adapter.lessons.header.holder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.storage.FirebaseStorage
import com.ribsky.common.alias.commonDrawable
import com.ribsky.common.utils.glide.ImageLoader.Companion.loadImage
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
        root.setOnClickListener { onClickListener.onClick() }
        circularProgressBar.setProgressWithAnimation(item.percent)
        tvTitle.text = item.name
        tvDescription.text = item.description
        imageView.loadImage(storage.getReferenceFromUrl(item.image)) {
            placeholder(commonDrawable.placeholder_content)
        }
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
