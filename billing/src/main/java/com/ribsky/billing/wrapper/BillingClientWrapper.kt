package com.ribsky.billing.wrapper

import androidx.appcompat.app.AppCompatActivity
import jp.alessandro.android.iab.BillingProcessor
import jp.alessandro.android.iab.Item
import jp.alessandro.android.iab.handler.PurchaseHandler

interface BillingClientWrapper {

    enum class Product(val sku: String) {
        WEEKLY("dymka_week"), MONTHLY("dymka_month"), LIFETIME("dymka_lifetime");

        companion object {
            fun fromSku(sku: String): Product? {
                return values().firstOrNull { it.sku == sku }
            }
        }
    }

    val billingProcessor: BillingProcessor?

    fun setup(activity: AppCompatActivity, handler: PurchaseHandler)

    fun purchase(sku: Product, callback: (Result<Unit>) -> Unit)

    fun getPurchasesList(callback: (Result<List<Item>>) -> Unit)

    fun getInventory(callback: (Result<List<Product>>) -> Unit)

    fun destroy()
}
