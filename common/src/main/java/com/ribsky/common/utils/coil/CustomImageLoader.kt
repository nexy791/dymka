package com.ribsky.common.utils.coil

import android.content.Context
import coil.ImageLoader
import coil.decode.SvgDecoder
import coil.request.CachePolicy
import com.ribsky.common.R
import io.github.rosariopfernandes.firecoil.StorageReferenceFetcher
import io.github.rosariopfernandes.firecoil.StorageReferenceKeyer

class CustomImageLoader {

    companion object {
        fun build(context: Context): ImageLoader {
            return ImageLoader.Builder(context)
                .components {
                    add(StorageReferenceKeyer())
                    add(StorageReferenceFetcher.Factory())
                    add(SvgDecoder.Factory())
                }
                .crossfade(true)
                .error(R.drawable.placeholder)
                .placeholder(R.drawable.placeholder)
                .diskCachePolicy(CachePolicy.ENABLED)
                .networkCachePolicy(CachePolicy.ENABLED)
                .memoryCachePolicy(CachePolicy.ENABLED)
                .build()
        }
    }
}
