package com.ribsky.shop.ui

import androidx.lifecycle.ViewModel
import com.ribsky.billing.manager.SubManager

class ShopViewModel(
    private val subManager: SubManager,
) : ViewModel() {

    val isSub get() = subManager.isSub()

    val sku get() = subManager.getSku()
}
