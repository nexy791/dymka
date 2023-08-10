package com.ribsky.top.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import coil.load
import coil.request.CachePolicy
import com.ribsky.common.alias.commonDrawable
import com.ribsky.top.databinding.LayoutTopBinding

class LayoutTopView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : FrameLayout(context, attrs, defStyleAttr) {

    private var binding: LayoutTopBinding? = null

    init {
        binding = LayoutTopBinding.inflate(LayoutInflater.from(context), this, true)
    }

    fun setUsername(username: String) {
        binding?.tvName?.text = username
    }

    fun setPhoto(photo: String) {
        binding?.ivAccount?.load(photo) {
            crossfade(true)
            placeholder(commonDrawable.cat)
            error(commonDrawable.cat)
            diskCachePolicy(CachePolicy.ENABLED)
            networkCachePolicy(CachePolicy.ENABLED)
            memoryCachePolicy(CachePolicy.ENABLED)
        }
    }

    fun setPlace(place: Int) {
        binding?.tvDescription?.apply {
            visibility = VISIBLE
            text = "$place місце"
        }
    }

    fun setScore(score: Int) {
        binding?.btnPlace?.text = score.toString()
    }

    fun setScoreDrawable(drawable: Int) {
        binding?.btnPlace?.setIconResource(drawable)
    }


}
