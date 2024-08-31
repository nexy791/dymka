package com.ribsky.common.utils.glide

import android.graphics.Bitmap
import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.bumptech.glide.load.Transformation
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import com.bumptech.glide.request.RequestOptions
import com.google.firebase.storage.StorageReference

class ImageLoader {

    companion object {

        inline fun ImageView.loadImage(
            url: String,
            requestOptions: RequestOptions.() -> Unit = {}
        ) {
            GlideApp.with(this)
                .load(url)
                .transition(withCrossFade())
                .centerCrop()
                .apply(RequestOptions().apply(requestOptions))
                .into(this)
        }

        inline fun ImageView.loadImage(
            storageReference: StorageReference,
            transformation: Transformation<Bitmap>? = null,
            requestOptions: RequestOptions.() -> Unit = {},
        ) {
            GlideApp.with(this)
                .load(storageReference)
                .transition(withCrossFade())
                .centerCrop()
                .apply { transformation?.let { transform(it) } }
                .apply(RequestOptions().apply(requestOptions))
                .into(this)
        }

        inline fun ImageView.loadImage(
            @DrawableRes drawable: Int,
            requestOptions: RequestOptions.() -> Unit = {}
        ) {
            GlideApp.with(this)
                .load(drawable)
                .transition(withCrossFade())
                .centerCrop()
                .apply(RequestOptions().apply(requestOptions))
                .into(this)
        }

    }

}