package com.ribsky.billing.wrapper

import androidx.appcompat.app.AppCompatActivity
import com.ribsky.billing.client.BillingClient
import com.ribsky.billing.manager.SubManager
import jp.alessandro.android.iab.BillingProcessor
import jp.alessandro.android.iab.Item
import jp.alessandro.android.iab.PurchaseType
import jp.alessandro.android.iab.handler.PurchaseHandler

class BillingClientWrapperImpl(
    private val billingClient: BillingClient,
    private val subManager: SubManager,
) : BillingClientWrapper {

    override val billingProcessor: BillingProcessor? get() = billingClient.billingProcessor

    override fun setup(activity: AppCompatActivity, handler: PurchaseHandler) {
        billingClient.setup(activity, handler)
    }

    override fun purchase(sku: BillingClientWrapper.Product, callback: (Result<Unit>) -> Unit) {
        val type = when (sku) {
            BillingClientWrapper.Product.WEEKLY -> PurchaseType.SUBSCRIPTION
            BillingClientWrapper.Product.MONTHLY -> PurchaseType.SUBSCRIPTION
            BillingClientWrapper.Product.LIFETIME -> PurchaseType.IN_APP
        }
        billingClient.purchase(sku.sku, type, callback)
    }

    override fun getPurchasesList(callback: (Result<List<Item>>) -> Unit) {
        val listInApp = listOf(
            BillingClientWrapper.Product.LIFETIME.sku
        )
        val listSubs = listOf(
            BillingClientWrapper.Product.WEEKLY.sku,
            BillingClientWrapper.Product.MONTHLY.sku
        )
        billingClient.getPurchasesList(listInApp, PurchaseType.IN_APP) { r ->
            r.fold(
                onSuccess = { list0 ->
                    billingClient.getPurchasesList(listSubs, PurchaseType.SUBSCRIPTION) { r ->
                        r.fold(
                            onSuccess = { list1 ->
                                val list = list0 + list1
                                callback(Result.success(list))
                            },
                            onFailure = {
                                callback(Result.failure(it))
                            }
                        )
                    }
                },
                onFailure = {
                    callback(Result.failure(it))
                }
            )
        }
    }

    override fun getInventory(callback: (Result<List<BillingClientWrapper.Product>>) -> Unit) {
        val inventoryList = mutableListOf<BillingClientWrapper.Product>()
        billingClient.getInventory(PurchaseType.IN_APP) { r ->
            r.fold(
                onSuccess = { list0 ->
                    inventoryList.addAll(
                        list0.mapNotNull { sku ->
                            BillingClientWrapper.Product.fromSku(
                                sku
                            )
                        }
                    )
                    billingClient.getInventory(PurchaseType.SUBSCRIPTION) { r ->
                        r.fold(
                            onSuccess = { list1 ->
                                inventoryList.addAll(
                                    list1.mapNotNull { sku ->
                                        BillingClientWrapper.Product.fromSku(
                                            sku
                                        )
                                    }
                                )
                                saveSubInfoToCache(inventoryList)
                                callback(Result.success(inventoryList))
                            },
                            onFailure = {
                                callback(Result.failure(it))
                            }
                        )
                    }
                },
                onFailure = {
                    callback(Result.failure(it))
                }
            )
        }
    }

    override fun destroy() {
        billingClient.destroy()
    }

    private fun saveSubInfoToCache(inventoryList: MutableList<BillingClientWrapper.Product>) {
        if (inventoryList.isNotEmpty()) {
            subManager.saveSub(true)
            subManager.updateSku(BillingClientWrapper.Product.fromSku(inventoryList.first().sku))
        } else {
            subManager.saveSub(false)
            subManager.updateSku(null)
        }
    }
}
