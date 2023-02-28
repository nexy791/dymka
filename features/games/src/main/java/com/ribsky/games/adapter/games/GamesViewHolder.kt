package com.ribsky.games.adapter.games

import android.annotation.SuppressLint
import android.graphics.PorterDuff
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.google.firebase.storage.FirebaseStorage
import com.ribsky.common.alias.commonDrawable
import com.ribsky.common.utils.ext.ResourceExt.Companion.toColor
import com.ribsky.games.databinding.ItemGameBinding
import com.ribsky.games.model.GameModel
import org.koin.java.KoinJavaComponent.inject

class GamesViewHolder(private val binding: ItemGameBinding) :
    RecyclerView.ViewHolder(binding.root) {

    private val storage: FirebaseStorage by inject(FirebaseStorage::class.java)

    @SuppressLint("NotifyDataSetChanged")
    fun bind(
        item: GameModel,
        onClickListener: GamesAdapter.OnClickListener,
    ) = with(binding) {
        root.setOnClickListener {
            onClickListener.onClick(item)
        }
        tvTitle.text = item.title

        tvIcon.load(storage.getReferenceFromUrl(item.image)) {
            placeholder(null)
            error(null)
        }

        if (item.isActive) {
            tvIcon.setColorFilter(item.getPrimaryColor().toColor(), PorterDuff.Mode.SRC_IN)
            image.setBackgroundColor(item.getBackgroundColor().toColor())
        } else {
            tvIcon.setColorFilter("#989898".toColor(), PorterDuff.Mode.SRC_IN)
            image.setBackgroundColor("#CCCCCC".toColor())
            checkbox.load(commonDrawable.ic_outline_lock_24)
            checkbox.setColorFilter("#666666".toColor(), PorterDuff.Mode.SRC_IN)
        }

        if (item.isPicked) {
            checkbox.setImageResource(commonDrawable.ic_round_check_circle_24)
        } else {
            checkbox.setImageResource(commonDrawable.ic_round_check_circle_outline_24)
        }

        if (item.isInProgress()) {
            checkbox.load(commonDrawable.ic_outline_draw_24)
            checkbox.setColorFilter("#666666".toColor(), PorterDuff.Mode.SRC_IN)
        }
    }
}
