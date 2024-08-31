package com.ribsky.tests.adapter.header

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ribsky.tests.databinding.ItemTestHeaderBinding

class TestHeaderViewHolder(private val binding: ItemTestHeaderBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(
        item: Int,
        onClickListener: TestHeaderAdapter.OnClickListener,
    ) = with(binding) {
        cardGame.setOnClickListener { onClickListener.onGameClick() }
        cardScore.setOnClickListener { onClickListener.onScoreInfoClick() }
        btnScore.setOnClickListener { onClickListener.onScoreInfoClick() }
        tvCoins.text = "$item/∞"
    }

    companion object {
        fun create(parent: ViewGroup): TestHeaderViewHolder {
            val binding = ItemTestHeaderBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            return TestHeaderViewHolder(binding)
        }
    }
}
