package com.ribsky.common.utils.ext

import android.content.*
import android.content.res.Resources
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.viewbinding.ViewBinding
import com.google.android.material.snackbar.Snackbar
import com.ribsky.common.ui.snackbar.CustomSnackbar

class ViewExt {

    companion object {

        val Int.dp: Int
            get() = (this / Resources.getSystem().displayMetrics.density).toInt()
        val Int.px: Int
            get() = (this * Resources.getSystem().displayMetrics.density).toInt()

        fun Context.toast(message: String) {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }

        fun ViewBinding.snackbar(message: String): Snackbar = CustomSnackbar.create {
            description { message }
            viewGroup { root as ViewGroup }
        }

        fun ViewBinding.snackbar(
            image: Int? = null,
            title: String? = null,
            message: String? = null,
        ): Snackbar = CustomSnackbar.create {
            message?.let { description { it } }
            title?.let { title { it } }
            image?.let { image { it } }
            viewGroup { root as ViewGroup }
        }

        fun Context.hideKeyboard(view: View) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }

        fun Context.showKeyboard(view: View) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
        }

        @Suppress("DEPRECATION")
        fun Context.vibrate(time: Long = 150) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                (getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager?)?.defaultVibrator?.vibrate(
                    VibrationEffect.createOneShot(time, VibrationEffect.DEFAULT_AMPLITUDE)
                )
            } else if (Build.VERSION.SDK_INT >= 26) {
                (getSystemService(Context.VIBRATOR_SERVICE) as Vibrator?)?.vibrate(
                    VibrationEffect.createOneShot(
                        time,
                        VibrationEffect.DEFAULT_AMPLITUDE
                    )
                )
            } else {
                (getSystemService(Context.VIBRATOR_SERVICE) as Vibrator?)?.vibrate(time)
            }
        }

        fun Context.copy(string: String) {
            try {
                val clipboardManager =
                    getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                clipboardManager.setPrimaryClip(ClipData.newPlainText("", string))
                if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.S_V2) {
                    Toast.makeText(this, "Скопійовано", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(this, "Помилка", Toast.LENGTH_SHORT).show()
            }
        }

        fun getInitials(name: String): String {
            val names = name.split(" ")
            return if (names.size > 1) {
                names[0].first().toString() + names[1].first().toString()
            } else {
                names[0].first().toString() + names[0].last().toString()
            }
        }

        fun String.formatUserName(isPrem: Boolean): String =
            if (isPrem) "$this \uD83C\uDDFA\uD83C\uDDE6" else this
    }
}
