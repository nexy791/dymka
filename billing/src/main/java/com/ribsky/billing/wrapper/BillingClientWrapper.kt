package com.ribsky.billing.wrapper

import androidx.appcompat.app.AppCompatActivity
import jp.alessandro.android.iab.BillingProcessor
import jp.alessandro.android.iab.Item
import jp.alessandro.android.iab.handler.PurchaseHandler

interface BillingClientWrapper {

    enum class Product(val sku: String) {
        WEEKLY("dymka_week"), MONTHLY("dymka_month"), LIFETIME("dymka_lifetime"),
        WEEKLY_FULL("dymka_week_full"), MONTHLY_FULL("dymka_month_full"), YEARLY_FULL("dymka_year_full"), LIFETIME_FULL("dymka_lifetime_full"),
        WEEKLY_LITE("dymka_week_lite"), MONTHLY_LITE("dymka_month_lite"), YEARLY_LITE("dymka_year_lite"), LIFETIME_LITE("dymka_lifetime_lite");

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
