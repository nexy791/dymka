package com.ribsky.common.utils.ext

import android.content.Context
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class AlertsExt {

    companion object {

        fun Context.showAlert(
            title: String,
            message: String,
            positiveButton: String,
            negativeButton: String,
            positiveAction: () -> Unit,
            negativeAction: () -> Unit,
            cancelable: Boolean = true,
        ) {
            MaterialAlertDialogBuilder(this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(positiveButton) { _, _ -> positiveAction() }
                .setNegativeButton(negativeButton) { _, _ -> negativeAction() }
                .setCancelable(cancelable)
                .show()
        }

        fun Context.showExitAlert(
            positiveAction: () -> Unit,
            negativeAction: () -> Unit,
        ) {
            MaterialAlertDialogBuilder(this)
                .setTitle("Вихід")
                .setMessage("Ти дійсно хочеш вийти?")
                .setPositiveButton("Вийти") { _, _ -> positiveAction() }
                .setNegativeButton("Відхилити") { _, _ -> negativeAction() }
                .setCancelable(true)
                .show()
        }

        fun Context.showErrorAlert(
            message: String?,
            positiveAction: (() -> Unit)?,
            negativeAction: () -> Unit,
        ) {
            MaterialAlertDialogBuilder(this).apply {
                setTitle("Помилка")
                setMessage(message.orEmpty().ifEmpty { "Невідома помилка" })
                positiveAction?.let { setPositiveButton("Спробувати ще раз") { _, _ -> it() } }
                setNegativeButton("Відхилити") { _, _ -> negativeAction() }
                setCancelable(false)
                show()
            }
        }
    }
}
