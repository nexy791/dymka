package com.ribsky.top.adapter.top.holder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.request.CachePolicy
import com.ribsky.common.alias.commonDrawable
import com.ribsky.common.utils.ext.ViewExt.Companion.formatUserName
import com.ribsky.top.adapter.top.TopAdapter
import com.ribsky.top.databinding.ItemTopLessonBinding
import com.ribsky.top.model.TopModel

class TopLessonViewHolder(private val binding: ItemTopLessonBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(item: TopModel, onClickListener: TopAdapter.OnClickListener) = with(binding) {
        root.setOnClickListener {
            onClickListener.onClick(item.id)
        }
        tvName.text = item.name.formatUserName(item.hasPrem)
        tvDescription.text = "${item.position} місце"
        ivAccount.load(item.image) {
            crossfade(true)
            placeholder(commonDrawable.cat)
            error(commonDrawable.cat)
            diskCachePolicy(CachePolicy.ENABLED)
            networkCachePolicy(CachePolicy.ENABLED)
            memoryCachePolicy(CachePolicy.ENABLED)
        }
        btnPlace.text = item.lessonsCount.toString()
    }

    companion object {
        fun create(parent: ViewGroup): TopLessonViewHolder {
            val binding = ItemTopLessonBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            return TopLessonViewHolder(binding)
        }
    }
}
