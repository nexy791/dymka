package com.ribsky.common.utils.ext

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment

class ResourceExt {

    companion object {

        fun String.toColor(): Int = Color.parseColor(this)

        fun String.toColorState(): ColorStateList = Color.parseColor(this).toStateList()

        fun Context.color(color: Int): Int = ContextCompat.getColor(this, color)

        fun Int.toStateList() = ColorStateList.valueOf(this)

        fun Context.drawable(drawable: Int) = ContextCompat.getDrawable(this, drawable)

        fun Fragment.drawable(drawable: Int) = requireContext().drawable(drawable)
    }
}
