package com.ribsky.article.ui.fragments.base

import androidx.viewbinding.ViewBinding
import com.google.firebase.storage.FirebaseStorage
import com.ribsky.common.alias.Inflate
import com.ribsky.common.base.BaseFragment
import com.ribsky.common.utils.ext.ResourceExt.Companion.toColor
import org.koin.java.KoinJavaComponent

abstract class BaseArticleFragment<VB : ViewBinding>(inflate: Inflate<VB>) :
    BaseFragment<BaseArticleViewModel, VB>(inflate) {

    protected val storage: FirebaseStorage by KoinJavaComponent.inject(FirebaseStorage::class.java)

    protected val text by lazy { arguments?.getString(TEXT, null) }
    protected val image by lazy { arguments?.getString(IMAGE, null) }
    protected val background by lazy {
        val bg = arguments?.getString(BACKGROUND, null)
        runCatching { bg?.toColor()!! }.getOrDefault("#64B5F6".toColor())
    }

    override fun onResume() {
        super.onResume()
        loadWithAnim()
    }

    override fun onPause() {
        super.onPause()
        clearWithAnim()
    }

    abstract fun loadWithAnim()

    abstract fun clearWithAnim()


    companion object {
        const val TEXT = "text"
        const val IMAGE = "image"
        const val BACKGROUND = "bg"
    }

}

