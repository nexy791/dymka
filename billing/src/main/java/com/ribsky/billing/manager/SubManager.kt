package com.ribsky.billing.manager

import com.ribsky.billing.wrapper.BillingClientWrapper

interface SubManager {

    fun isSub(): Boolean

    fun saveSub(isSub: Boolean)

    fun getSku(): BillingClientWrapper.Product?

    fun updateSku(sku: BillingClientWrapper.Product?)

    fun saveDiscountState(isDiscount: Boolean)

    fun isDiscount(): Boolean

    fun isSubWasRestored(): Boolean

    fun saveSubWasRestored(isSubWasRestored: Boolean)
}
