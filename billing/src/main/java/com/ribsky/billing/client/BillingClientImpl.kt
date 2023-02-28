package com.ribsky.billing.client

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import jp.alessandro.android.iab.*
import jp.alessandro.android.iab.handler.ItemDetailsHandler
import jp.alessandro.android.iab.handler.PurchaseHandler
import jp.alessandro.android.iab.handler.PurchasesHandler
import jp.alessandro.android.iab.handler.StartActivityHandler
import jp.alessandro.android.iab.logger.SystemLogger

class BillingClientImpl(
    private val context: Context,
) : BillingClient {

    companion object {
        private const val KEY =
            "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAm4XMlKoL+uLKO81p6nmgK6Wvoa8QjBBTb8vQ0TeE+aTwKmQNRS/Um0FghddKaK6RuNAKeE4popuSBxmNWz0JLNqmpbXLD3JCvLzD0IXtlQRzs6+FDFVB+udHJNYpkxptu8MxU92kK37BO0T9phJG2CtJ/dQWpt7lKYy8lt0Zc0+na1Z2GVKq+HUNvhtE85X5mbH35PYYlqWroZP965FqYu05L5zRzjMI53sI3CNIuxq8KpmPN072wk2t9iMTJTTEANXIUDUcyHD0uprP9SUQggBg8B2u0gOp64XSkNNBwAC+dB9paFcPJLUc88C2NkQqpjJhYQwTuVKpjdSJ5eqvuwIDAQAB"
    }

    override var billingProcessor: BillingProcessor? = null
    private var activity: AppCompatActivity? = null

    override fun setup(activity: AppCompatActivity, handler: PurchaseHandler) {
        val builder = BillingContext.Builder()
            .setContext(context.applicationContext)
            .setPublicKeyBase64(KEY)
            .setApiVersion(BillingApi.VERSION_5)
            .setLogger(SystemLogger())
        billingProcessor = BillingProcessor(builder.build(), handler)
        this.activity = activity
    }

    override fun purchase(sku: String, type: PurchaseType, callback: (Result<Unit>) -> Unit) {
        billingProcessor?.startPurchase(
            activity,
            0,
            sku,
            type,
            "",
            object : StartActivityHandler {
                override fun onSuccess() {
                    callback(Result.success(Unit))
                }

                override fun onError(e: BillingException) {
                    callback(Result.failure(e))
                }
            }
        )
    }

    override fun getPurchasesList(
        sku: List<String>,
        type: PurchaseType,
        callback: (Result<List<Item>>) -> Unit,
    ) {
        billingProcessor?.getItemDetails(
            type,
            ArrayList(sku),
            object : ItemDetailsHandler {
                override fun onSuccess(itemDetails: ItemDetails?) {
                    callback(Result.success(itemDetails!!.all!!))
                }

                override fun onError(e: BillingException?) {
                    callback(Result.failure(e!!))
                }
            }
        )
    }

    override fun getInventory(type: PurchaseType, callback: (Result<List<String>>) -> Unit) {
        billingProcessor?.getPurchases(
            type,
            object : PurchasesHandler {
                override fun onSuccess(purchases: Purchases) {
                    callback.invoke(Result.success(purchases.all.map { it.sku }))
                }

                override fun onError(e: BillingException) {
                    callback.invoke(Result.failure(e))
                }
            }
        )
    }

    override fun destroy() {
        billingProcessor?.release()
    }
}
