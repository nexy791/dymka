package com.ribsky.common.utils.glide

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.graphics.Matrix
import android.graphics.Paint
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation
import java.security.MessageDigest
import kotlin.math.floor

class GrayscaleTransformation(
    private val percent: Float,
) : BitmapTransformation() {

    override fun equals(other: Any?) = other is GrayscaleTransformation && other.percent == percent

    override fun hashCode() = percent.hashCode() + GrayscaleTransformation::class.java.hashCode()

    override fun updateDiskCacheKey(messageDigest: MessageDigest) {
        messageDigest.update(percent.toString().toByteArray())
    }

    override fun transform(
        pool: BitmapPool,
        toTransform: Bitmap,
        outWidth: Int,
        outHeight: Int
    ): Bitmap {
        val toTransform = Bitmap.createScaledBitmap(toTransform, outWidth, outHeight, true)

        if (percent == 0f) return toTransform
        val paint = Paint(Paint.ANTI_ALIAS_FLAG or Paint.FILTER_BITMAP_FLAG)
        paint.colorFilter = COLOR_FILTER

        val fortyPercentHeight = floor(toTransform.height * percent / 100.0).toInt()
        val croppedBitmap =
            Bitmap.createBitmap(toTransform, 0, 0, toTransform.width, fortyPercentHeight)
        val blackAndWhiteBitmap = monoChrome(croppedBitmap)

        return overlay(toTransform, blackAndWhiteBitmap)
    }

    private fun monoChrome(bitmap: Bitmap): Bitmap {
        val bmpMonochrome =
            Bitmap.createBitmap(bitmap.width, bitmap.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bmpMonochrome)
        val ma = ColorMatrix()
        ma.setSaturation(0.2f)
        val paint = Paint()
        paint.colorFilter = ColorMatrixColorFilter(ma)
        canvas.drawBitmap(bitmap, 0f, 0f, paint)
        return bmpMonochrome
    }

    private fun overlay(bmp1: Bitmap, bmp2: Bitmap): Bitmap {
        val bmp3 = bmp1.copy(Bitmap.Config.ARGB_8888, true)
        val canvas = Canvas(bmp3)
        canvas.drawBitmap(bmp2, Matrix(), null)
        return bmp3
    }

    private companion object {
        val COLOR_FILTER = ColorMatrixColorFilter(ColorMatrix().apply { setSaturation(0.2f) })
    }
}
