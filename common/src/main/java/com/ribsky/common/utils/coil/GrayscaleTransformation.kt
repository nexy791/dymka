package com.ribsky.common.utils.coil

import android.graphics.*
import coil.size.Size
import coil.transform.Transformation
import kotlin.math.floor

class GrayscaleTransformation(
    private val percent: Float,
) : Transformation {

    override val cacheKey: String = GrayscaleTransformation::class.java.name

    override suspend fun transform(input: Bitmap, size: Size): Bitmap {
        if (percent == 0f) return input
        val paint = Paint(Paint.ANTI_ALIAS_FLAG or Paint.FILTER_BITMAP_FLAG)
        paint.colorFilter = COLOR_FILTER

        val fortyPercentHeight = floor(input.height * percent / 100.0).toInt()
        val croppedBitmap =
            Bitmap.createBitmap(input, 0, 0, input.width, fortyPercentHeight)
        val blackAndWhiteBitmap = monoChrome(croppedBitmap)

        return overlay(input, blackAndWhiteBitmap)
    }

    override fun equals(other: Any?) = other is GrayscaleTransformation

    override fun hashCode() = javaClass.hashCode()

    override fun toString() = "GrayscaleTransformation()"

    private fun monoChrome(bitmap: Bitmap): Bitmap {
        val bmpMonochrome =
            Bitmap.createBitmap(bitmap.width, bitmap.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bmpMonochrome)
        val ma = ColorMatrix()
        ma.setSaturation(0f)
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
        val COLOR_FILTER = ColorMatrixColorFilter(ColorMatrix().apply { setSaturation(0f) })
    }
}
