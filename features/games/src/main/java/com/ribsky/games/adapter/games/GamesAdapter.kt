package com.ribsky.games.adapter.games

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.ribsky.games.adapter.diff.DiffCallbackGame
import com.ribsky.games.databinding.ItemGameBinding
import com.ribsky.games.model.GameModel

class GamesAdapter(private val onClickListener: OnClickListener) :
    ListAdapter<GameModel, GamesViewHolder>(DiffCallbackGame) {

    @SuppressLint("NotifyDataSetChanged")
    fun setPicked(game: GameModel) {
        currentList.forEach {
            it.isPicked = it.id == game.id
        }
        notifyDataSetChanged()
    }

    fun getPicked(): GameModel = currentList.find { it.isPicked }!!

    fun interface OnClickListener {
        fun onClick(game: GameModel)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GamesViewHolder =
        GamesViewHolder(
            ItemGameBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(holder: GamesViewHolder, position: Int) {
        holder.bind(getItem(position), onClickListener)
    }
}
