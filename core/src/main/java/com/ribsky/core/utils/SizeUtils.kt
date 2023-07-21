package com.ribsky.core.utils

import android.content.res.Resources

class SizeUtils {

    companion object {

        val Int.dp: Int
            get() = (this / Resources.getSystem().displayMetrics.density).toInt()

        val Int.px: Int
            get() = (this * Resources.getSystem().displayMetrics.density).toInt()

    }

}