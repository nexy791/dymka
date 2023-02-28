package com.ribsky.billing.manager

import android.content.SharedPreferences
import androidx.core.content.edit
import com.ribsky.billing.wrapper.BillingClientWrapper

class SubMangerImpl(
    private val sharedPreferences: SharedPreferences,
) : SubManager {

    companion object {
        private const val TAG = "SUB_KEY"
        private const val TAG_SKU = "SUB_KEY_SKU"
    }

    override fun isSub(): Boolean = sharedPreferences.getBoolean(TAG, false)

    override fun saveSub(isSub: Boolean) {
        sharedPreferences.edit {
            putBoolean(TAG, isSub)
        }
    }

    override fun getSku(): BillingClientWrapper.Product? =
        sharedPreferences.getString(TAG_SKU, "")?.let {
            BillingClientWrapper.Product.fromSku(it)
        }

    override fun updateSku(sku: BillingClientWrapper.Product?) {
        sharedPreferences.edit {
            putString(TAG_SKU, sku?.sku)
        }
    }
}
