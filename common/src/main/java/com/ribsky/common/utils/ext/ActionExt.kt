package com.ribsky.common.utils.ext

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.widget.Toast
import androidx.core.app.ShareCompat
import androidx.fragment.app.Fragment
import com.ribsky.common.utils.Const
import com.ribsky.common.utils.Const.Companion.TEXT_SHARE

class ActionExt {

    companion object {

        fun Context.shareApp() {
            ShareCompat
                .IntentBuilder(this)
                .setType("text/plain")
                .setChooserTitle("Поділитись")
                .setText(TEXT_SHARE)
                .startChooser()
        }

        fun Context.sendEmail(email: String = Const.EMAIL, subject: String, text: String) {
            val intent = Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse("mailto:")
                putExtra(Intent.EXTRA_EMAIL, arrayOf(email))
                putExtra(Intent.EXTRA_SUBJECT, subject)
                putExtra(Intent.EXTRA_TEXT, text)
            }
            try {
                startActivity(Intent.createChooser(intent, "Відправити листа"))
            } catch (e: Exception) {
                Toast.makeText(this, "Помилка", Toast.LENGTH_SHORT).show()
            }
        }

        fun Fragment.sendEmail(email: String = Const.EMAIL, subject: String, text: String) {
            context?.sendEmail(email, subject, text)
        }

        fun Context.openWifiSettings() {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                try {
                    startActivity(Intent(Settings.Panel.ACTION_WIFI))
                } catch (e: ActivityNotFoundException) {
                    startActivity(Intent(Settings.ACTION_WIFI_SETTINGS))
                }
            } else {
                startActivity(Intent(Settings.ACTION_WIFI_SETTINGS))
            }
        }

        fun Fragment.openWifiSettings() {
            context?.openWifiSettings()
        }

        fun Activity.openAppPage() {
            try {
                startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("market://details?id=$packageName")
                    )
                )
            } catch (e: ActivityNotFoundException) {
                startActivity(
                    Intent.createChooser(
                        Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse(Const.APP_LINK)
                        ),
                        "Відкрити за допомогою"
                    )
                )
            }
        }

        fun Activity.openSubscriptions(sku: String) {
            try {
                startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("https://play.google.com/store/account/subscriptions")
                    )
                )
            } catch (e: ActivityNotFoundException) {
                startActivity(
                    Intent.createChooser(
                        Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse("https://play.google.com/store/account/subscriptions?sku=$sku&package=$packageName")
                        ),
                        "Відкрити за допомогою"
                    )
                )
            }
        }

        fun Activity.openLink(link: String) {
            startActivity(
                Intent.createChooser(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse(link)
                    ),
                    "Відкрити за допомогою"
                )
            )
        }
    }
}
