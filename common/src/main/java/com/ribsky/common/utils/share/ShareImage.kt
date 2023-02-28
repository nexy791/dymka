package com.ribsky.common.utils.share

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.view.View
import androidx.core.content.FileProvider
import java.io.File
import java.util.*

@SuppressLint("StaticFieldLeak")
class ShareImage(private val builder: Builder) {

    companion object Builder {

        private var description: String? = null
        private var view: View? = null
        private var context: Context? = null

        private var file: File? = null

        fun addDescription(description: String) = apply {
            Builder.description = description
        }

        fun addView(view: View) = apply {
            Builder.view = view
        }

        fun addContext(context: Context) = apply {
            Builder.context = context
        }

        fun build(): ShareImage {
            if (view != null) buildImage()
            return ShareImage(this)
        }

        private fun buildImage() {

            val image =
                Bitmap.createBitmap(view!!.width, view!!.height, Bitmap.Config.ARGB_8888)

            view!!.draw(Canvas(image))

            runCatching {
                val file = File(context?.cacheDir, UUID.randomUUID().toString() + ".png")

                file.outputStream().use { out ->
                    image.compress(Bitmap.CompressFormat.PNG, 100, out)
                    out.flush()
                }

                Builder.file = file
            }
        }

        private fun createShareIntent(context: Context, file: File? = null) {

            val stickerAssetUri =
                file?.let { FileProvider.getUriForFile(context, context.packageName, it) }

            val shareIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, description)
                flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
                putExtra(Intent.EXTRA_STREAM, stickerAssetUri)
                type = "image/*"
            }

            context.startActivity(Intent.createChooser(shareIntent, null))
        }
    }

    fun share() = createShareIntent(context!!, file)

    fun clear() {
        description = null
        view = null
        file = null
        context = null
    }
}
