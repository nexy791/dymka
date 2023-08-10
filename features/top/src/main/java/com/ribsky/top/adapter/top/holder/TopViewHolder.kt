package com.ribsky.top.adapter.top.holder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ribsky.common.alias.commonDrawable
import com.ribsky.common.utils.ext.ViewExt.Companion.formatUserName
import com.ribsky.top.adapter.top.TopAdapter
import com.ribsky.top.databinding.ItemTopBinding
import com.ribsky.top.model.TopModel

class TopViewHolder(private val binding: ItemTopBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(item: TopModel, onClickListener: TopAdapter.OnClickListener) = with(binding) {
        topView.apply {
            setOnClickListener {
                onClickListener.onClick(item.id)
            }
            setUsername(item.name.formatUserName(item.hasPrem))
            setPhoto(item.image)
            setPlace(item.position)
            setScore(
                when (item.type) {
                    TopModel.ViewType.TEST -> item.score
                    TopModel.ViewType.STREAK -> item.streak
                    TopModel.ViewType.STARS -> item.starsCount
                }
            )
            setScoreDrawable(
                when (item.type) {
                    TopModel.ViewType.TEST -> commonDrawable.ic_outline_collections_bookmark_24
                    TopModel.ViewType.STREAK -> commonDrawable.ic_round_local_fire_department_24
                    TopModel.ViewType.STARS -> commonDrawable.ic_round_star_border_24
                }
            )
        }
        root.setOnClickListener {
            onClickListener.onClick(item.id)
        }
    }

    companion object {
        fun create(parent: ViewGroup): TopViewHolder {
            val binding = ItemTopBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            return TopViewHolder(binding)
        }
    }
}
