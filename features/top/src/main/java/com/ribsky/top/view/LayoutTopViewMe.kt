package com.ribsky.top.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import coil.load
import coil.request.CachePolicy
import com.ribsky.common.alias.commonDrawable
import com.ribsky.top.databinding.LayoutTopMeBinding

class LayoutTopViewMe @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : FrameLayout(context, attrs, defStyleAttr) {

    private var binding: LayoutTopMeBinding? = null

    init {
        binding = LayoutTopMeBinding.inflate(LayoutInflater.from(context), this, true)
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

    fun setScore(score: Int) {
        binding?.btnPlace?.text = score.toString()
    }

    fun setScoreDrawable(drawable: Int) {
        binding?.btnPlace?.setIconResource(drawable)
    }

}
