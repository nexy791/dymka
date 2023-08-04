package studio.zewei.willy.animationratingbar

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager

/**
 * Created by Willy on 2020/12/01.
 */

fun Context.dpToPx(dp: Int): Int = (dp * resources.displayMetrics.density).toInt()

