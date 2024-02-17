package com.ribsky.billing.wrapper

import android.app.Activity
import com.revenuecat.purchases.Purchases
import com.revenuecat.purchases.models.StoreProduct
import com.ribsky.billing.client.BillingClient
import com.ribsky.billing.manager.SubManager

class BillingClientWrapperImpl(
    private val billingClient: BillingClient,
    private val subManager: SubManager,
) : BillingClientWrapper {

    override fun setup(activity: Activity) {
        billingClient.setup(activity)
    }

    override fun purchase(product: StoreProduct, callback: (Result<Unit>) -> Unit) {
        billingClient.purchase(product, callback)
    }

    override fun getPurchasesList(callback: (Result<List<StoreProduct>>) -> Unit) {
        val listInApp: List<String> = listOf(
            BillingClientWrapper.Product.LIFETIME.sku,
            BillingClientWrapper.Product.LIFETIME_FULL.sku,
            BillingClientWrapper.Product.LIFETIME_LITE.sku
        )
        val listSubs: List<String> = listOf(
            BillingClientWrapper.Product.WEEKLY.sku,
            BillingClientWrapper.Product.MONTHLY.sku,
            BillingClientWrapper.Product.WEEKLY_FULL.sku,
            BillingClientWrapper.Product.MONTHLY_FULL.sku,
            BillingClientWrapper.Product.WEEKLY_LITE.sku,
            BillingClientWrapper.Product.MONTHLY_LITE.sku,
            BillingClientWrapper.Product.YEARLY_LITE.sku,
            BillingClientWrapper.Product.YEARLY_FULL.sku
        )
        val products = listInApp + listSubs
        billingClient.getPurchasesList(products) { r ->
            r.fold(
                onSuccess = {
                    callback(Result.success(it))
                },
                onFailure = {
                    callback(Result.failure(it))
                }
            )
        }
    }

    override fun getInventory(callback: (Result<List<BillingClientWrapper.Product>>) -> Unit) {
        syncPurchases {
            billingClient.getInventory { r ->
                r.fold(
                    onSuccess = { list ->
                        val productList =
                            list.map { BillingClientWrapper.Product.fromSku(it) ?: BillingClientWrapper.Product.PROMO }
                        saveSubInfoToCache(productList)
                        callback(Result.success(productList))
                    },
                    onFailure = {
                        callback(Result.failure(it))
                    }
                )
            }
        }
    }

    private fun syncPurchases(callback: (Result<Unit>) -> Unit) {
        if (!subManager.isSubWasRestored()) {
            billingClient.syncPurchases {
                subManager.saveSubWasRestored(it.isSuccess)
                if (it.isFailure) {
                    callback(Result.failure(it.exceptionOrNull()!!))
                } else {
                    callback(Result.success(Unit))
                }
            }
        } else {
            callback(Result.success(Unit))
        }
    }

    override fun restorePurchases(callback: (Result<Unit>) -> Unit) {
        billingClient.restorePurchases(callback)
    }

    override fun destroy() {
        billingClient.destroy()
    }

    private fun saveSubInfoToCache(inventoryList: List<BillingClientWrapper.Product>) {
        val isSub = inventoryList.isNotEmpty()
        subManager.saveSub(isSub)
        if (isSub) {
            subManager.updateSku(BillingClientWrapper.Product.fromSku(inventoryList.first().sku))
        } else {
            subManager.updateSku(null)
        }
    }
}
