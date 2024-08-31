package com.ribsky.feed.adapter.paragraph.holder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.storage.FirebaseStorage
import com.ribsky.common.alias.commonDrawable
import com.ribsky.common.utils.glide.GrayscaleTransformation
import com.ribsky.common.utils.glide.ImageLoader.Companion.loadImage
import com.ribsky.domain.model.paragraph.BaseParagraphModel
import com.ribsky.feed.adapter.paragraph.ParagraphAdapter
import com.ribsky.feed.databinding.ItemParagraphBinding
import org.koin.java.KoinJavaComponent

class ParagraphViewHolder(private val binding: ItemParagraphBinding) :
    RecyclerView.ViewHolder(binding.root) {

    private val storage: FirebaseStorage by KoinJavaComponent.inject(FirebaseStorage::class.java)

    fun bind(item: BaseParagraphModel, onClickListener: ParagraphAdapter.OnClickListener) =
        with(binding) {
            root.setOnClickListener {
                onClickListener.onClick(item)
            }
            tvTitle.text = item.name
            background.loadImage(storage.getReferenceFromUrl(item.image), transformation = GrayscaleTransformation(100 - item.percent)) {
                placeholder(commonDrawable.placeholder_content)
            }
        }

    companion object {
        fun create(parent: ViewGroup): ParagraphViewHolder {
            val binding = ItemParagraphBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            return ParagraphViewHolder(binding)
        }
    }
}
