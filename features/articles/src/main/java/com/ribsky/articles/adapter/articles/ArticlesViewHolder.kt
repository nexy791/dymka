package com.ribsky.articles.adapter.articles

import android.annotation.SuppressLint
import androidx.core.view.isGone
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.storage.FirebaseStorage
import com.ribsky.articles.databinding.ItemArticleBinding
import com.ribsky.common.R
import com.ribsky.common.alias.commonDrawable
import com.ribsky.common.utils.glide.ImageLoader.Companion.loadImage
import com.ribsky.domain.model.article.BaseArticleModel
import org.koin.java.KoinJavaComponent.inject

class ArticlesViewHolder(private val binding: ItemArticleBinding) :
    RecyclerView.ViewHolder(binding.root) {

    private val storage: FirebaseStorage by inject(FirebaseStorage::class.java)

    @SuppressLint("NotifyDataSetChanged")
    fun bind(
        item: BaseArticleModel,
        onClickListener: ArticlesAdapter.OnClickListener
    ) = with(binding) {
        root.setOnClickListener { onClickListener.onClick(item) }
        tvTitle.text = item.name
        tvDescription.text = item.description

        imageView.loadImage(storage.getReferenceFromUrl(item.image)) {
            error(R.drawable.placeholder)
            placeholder(commonDrawable.placeholder_content)
        }

        btnPrem.isGone = !item.hasPrem
        btnDev.isGone = !item.isInProgress()

    }
}
