package com.ribsky.share.ui.base

import android.view.View
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding
import com.ribsky.analytics.Analytics
import com.ribsky.common.alias.InflateActivity
import com.ribsky.common.base.BaseActivity
import com.ribsky.common.utils.Const
import com.ribsky.common.utils.share.ShareImage

abstract class BaseShareActivity<VM : ViewModel, VB : ViewBinding>(
    private val inflate: InflateActivity<VB>,
) : BaseActivity<VM, VB>(inflate) {

    private var shareImage: ShareImage? = null

    private fun initShareView(view: View) {
        shareImage = ShareImage
            .addContext(this)
            .addView(view)
            .addDescription(Const.TEXT_SHARE)
            .build()
    }

    protected fun share(view: View, event: Analytics.Event) {
        Analytics.logEvent(event)
        initShareView(view)
        shareImage?.share()
    }

    override fun clear() {
        shareImage?.clear()
        shareImage = null
    }
}
