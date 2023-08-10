package com.ribsky.billing.client

import android.app.Activity
import com.revenuecat.purchases.models.StoreProduct

interface BillingClient {

    fun setup(activity: Activity)

    fun purchase(product: StoreProduct, callback: (Result<Unit>) -> Unit)

    fun getPurchasesList(
        sku: List<String>,
        callback: (Result<List<StoreProduct>>) -> Unit,
    )

    fun getInventory(callback: (Result<List<String>>) -> Unit)

    fun destroy()

    fun syncPurchases(callback: (Result<Unit>) -> Unit)
    fun restorePurchases(callback: (Result<Unit>) -> Unit)


}
