package com.ribsky.top.adapter.header.holder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ribsky.common.alias.commonDrawable
import com.ribsky.common.utils.ext.ViewExt.Companion.formatUserName
import com.ribsky.common.utils.glide.ImageLoader.Companion.loadImage
import com.ribsky.top.adapter.header.TopHeaderAdapter
import com.ribsky.top.databinding.ItemProfileBinding
import com.ribsky.top.model.TopModel
import com.ribsky.top.ui.base.ViewType

class TopHeaderViewHolder(private val binding: ItemProfileBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(
        item: TopModel,
        onClickListener: TopHeaderAdapter.OnClickListener,
        type: ViewType,
        time: String,
    ) = with(binding) {
        root.setOnClickListener {
            onClickListener.onClick()
        }
        tvName.text = item.name.formatUserName(item.hasPrem)
        tvDescription.text = if (item.position == -1) {
            "50+ місце"
        } else {
            "${item.position} місце"
        }
        tvDate.text = "Останнє оновлення: $time"
        ivAccount.loadImage(item.image) {
            placeholder(commonDrawable.cat)
            error(commonDrawable.cat)
        }
        when (type) {
            ViewType.LESSONS -> btnPlace.apply {
                text = item.lessonsCount.toString()
                setIconResource(commonDrawable.ic_outline_history_edu_24)
            }

            ViewType.SCORES -> btnPlace.apply {
                text = item.score.toString()
                setIconResource(commonDrawable.ic_outline_collections_bookmark_24)
            }

            ViewType.STREAK -> btnPlace.apply {
                text = item.streak.toString()
                setIconResource(commonDrawable.ic_round_local_fire_department_24)
            }

            ViewType.STARS -> btnPlace.apply {
                text = item.starsCount.toString()
                setIconResource(commonDrawable.ic_round_star_border_24)
            }
        }
    }

    companion object {
        fun create(parent: ViewGroup): TopHeaderViewHolder {
            val binding = ItemProfileBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            return TopHeaderViewHolder(binding)
        }
    }
}
