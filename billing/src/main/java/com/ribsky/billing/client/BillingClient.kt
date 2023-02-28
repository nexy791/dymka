package com.ribsky.billing.client

import androidx.appcompat.app.AppCompatActivity
import jp.alessandro.android.iab.BillingProcessor
import jp.alessandro.android.iab.Item
import jp.alessandro.android.iab.PurchaseType
import jp.alessandro.android.iab.handler.PurchaseHandler

interface BillingClient {

    val billingProcessor: BillingProcessor?

    fun setup(activity: AppCompatActivity, handler: PurchaseHandler)

    fun purchase(sku: String, type: PurchaseType, callback: (Result<Unit>) -> Unit)

    fun getPurchasesList(
        sku: List<String>,
        type: PurchaseType,
        callback: (Result<List<Item>>) -> Unit,
    )

    fun getInventory(type: PurchaseType, callback: (Result<List<String>>) -> Unit)

    fun destroy()
}
