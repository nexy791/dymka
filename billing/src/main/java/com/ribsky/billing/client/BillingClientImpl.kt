package com.ribsky.billing.client

import android.app.Activity
import com.revenuecat.purchases.PurchaseParams
import com.revenuecat.purchases.Purchases
import com.revenuecat.purchases.getCustomerInfoWith
import com.revenuecat.purchases.getProductsWith
import com.revenuecat.purchases.models.StoreProduct
import com.revenuecat.purchases.purchaseWith
import com.revenuecat.purchases.restorePurchasesWith
import com.revenuecat.purchases.syncPurchasesWith

class BillingClientImpl : BillingClient {

    private var activity: Activity? = null

    override fun setup(activity: Activity) {
        this.activity = activity
    }

    override fun purchase(product: StoreProduct, callback: (Result<Unit>) -> Unit) {
        if (activity != null) {
            Purchases.sharedInstance.purchaseWith(
                PurchaseParams.Builder(activity!!, product).build(),
                onError = { error, userCancelled ->
                    if (!userCancelled) callback(Result.failure(Throwable(error.message)))
                },
                onSuccess = { _, customerInfo ->
                    if (customerInfo.entitlements.active.isNotEmpty()) callback(Result.success(Unit))
                }
            )
        } else {
            callback(Result.failure(Throwable("Activity is null")))
        }
    }

    override fun getPurchasesList(
        sku: List<String>,
        callback: (Result<List<StoreProduct>>) -> Unit,
    ) {
        Purchases.sharedInstance.getProductsWith(sku,
            onError = { callback(Result.failure(Throwable(it.message))) },
            onGetStoreProducts = { callback(Result.success(it)) }
        )
    }

    override fun getInventory(callback: (Result<List<String>>) -> Unit) {
        Purchases.sharedInstance.getCustomerInfoWith(
            onError = {
                callback.invoke(Result.failure(Throwable(it.message)))
            },
            onSuccess = {
                callback.invoke(Result.success(it.entitlements.active.values.map { p -> p.productIdentifier }))
            })
    }

    override fun destroy() {
        activity = null
    }

    override fun restorePurchases(callback: (Result<Unit>) -> Unit) {
        Purchases.sharedInstance.restorePurchasesWith(
            onSuccess = {
                callback(Result.success(Unit))
            },
            onError = { error ->
                callback(Result.failure(Throwable(error.message)))
            }
        )
    }

    override fun syncPurchases(callback: (Result<Unit>) -> Unit) {
        Purchases.sharedInstance.syncPurchasesWith(
            onSuccess = {
                callback(Result.success(Unit))
            },
            onError = { error ->
                callback(Result.failure(Throwable(error.message)))
            }
        )
    }

}
