package com.ribsky.common.utils.ext

import android.content.*
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.transition.AutoTransition
import androidx.transition.TransitionManager
import androidx.viewbinding.ViewBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.snackbar.Snackbar
import com.ribsky.common.ui.snackbar.CustomSnackbar

class ViewExt {

    companion object {

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

        fun Fragment.hideKeyboard(view: View) = requireContext().hideKeyboard(view)

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

        fun Int.formatDays(): String =
            if (this == 1) "$this день" else "$this днів"

        fun Int.formatStars(): String = when (this) {
            1 -> "$this зірка"
            2, 3, 4 -> "$this зірки"
            else -> "$this зірок"
        }

        fun Int.formatStarsV2(): String = when (this) {
            1 -> "$this зірочку"
            2, 3, 4 -> "$this зірочки"
            else -> "$this зірок"
        }

        fun Fragment.showBottomSheetDialog(dialog: BottomSheetDialogFragment) {
            activity?.let { a ->
                (a as? AppCompatActivity?)?.showBottomSheetDialog(dialog)
            }
        }

        fun AppCompatActivity.showBottomSheetDialog(dialog: BottomSheetDialogFragment) {
            if (supportFragmentManager.isDestroyed) return
            if (supportFragmentManager.findFragmentByTag(dialog.tag) != null) return
            if (dialog.isAdded) return
            dialog.show(supportFragmentManager, dialog.tag)
        }

        fun ViewGroup.show() {
            TransitionManager.beginDelayedTransition(this, AutoTransition())
            visibility = View.VISIBLE
        }

        fun ViewGroup.hide() {
            TransitionManager.beginDelayedTransition(this, AutoTransition())
            visibility = View.GONE
        }

    }
}
