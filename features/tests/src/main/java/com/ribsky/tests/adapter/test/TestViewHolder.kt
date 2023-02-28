package com.ribsky.tests.adapter.test

import android.graphics.PorterDuff
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.google.firebase.storage.FirebaseStorage
import com.ribsky.common.alias.commonDrawable
import com.ribsky.common.utils.ext.ResourceExt.Companion.toColor
import com.ribsky.tests.databinding.ItemTestBinding
import com.ribsky.tests.model.TestModel
import org.koin.java.KoinJavaComponent

class TestViewHolder(private val binding: ItemTestBinding) :
    RecyclerView.ViewHolder(binding.root) {

    private val storage: FirebaseStorage by KoinJavaComponent.inject(FirebaseStorage::class.java)
    fun bind(
        item: TestModel,
        onClickListener: TestAdapter.OnClickListener,
    ) = with(binding) {
        root.setOnClickListener {
            onClickListener.onClick(item)
        }
        tvTitle.text = item.title
        if (!item.isInProgress()) {
            icNext.load(
                when (item.isActive) {
                    true -> commonDrawable.ic_round_navigate_next_24
                    false -> commonDrawable.ic_outline_lock_24
                }
            )
        } else {
            icNext.load(commonDrawable.ic_outline_draw_24)
        }
        tvIcon.load(storage.getReferenceFromUrl(item.image)) {
            placeholder(null)
            error(null)
        }
        if (item.isActive) {
            tvIcon.setColorFilter(
                item.getPrimaryColor().toColor(),
                PorterDuff.Mode.SRC_IN
            )
            image.setBackgroundColor(item.getBackgroundColor().toColor())
        } else {
            tvIcon.setColorFilter(
                "#989898".toColor(),
                PorterDuff.Mode.SRC_IN
            )
            image.setBackgroundColor("#CCCCCC".toColor())
        }
    }

    companion object {
        fun create(parent: ViewGroup): TestViewHolder {
            val binding = ItemTestBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            return TestViewHolder(binding)
        }
    }
}
