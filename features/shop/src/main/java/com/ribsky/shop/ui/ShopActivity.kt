package com.ribsky.shop.ui

import android.content.Intent
import androidx.transition.AutoTransition
import androidx.transition.TransitionManager
import com.redmadrobot.lib.sd.LoadingStateDelegate
import com.ribsky.billing.wrapper.BillingClientWrapper
import com.ribsky.common.base.BaseActivity
import com.ribsky.common.utils.ext.ActionExt.Companion.openSubscriptions
import com.ribsky.common.utils.ext.ActionExt.Companion.sendEmail
import com.ribsky.common.utils.ext.AlertsExt.Companion.showAlert
import com.ribsky.common.utils.ext.AlertsExt.Companion.showErrorAlert
import com.ribsky.common.utils.party.Party
import com.ribsky.navigation.features.LoaderNavigation
import com.ribsky.navigation.features.ShopNavigation
import com.ribsky.shop.databinding.ActivityShopBinding
import com.ribsky.shop.dialogs.sub.SubDialog
import jp.alessandro.android.iab.Item
import jp.alessandro.android.iab.handler.PurchaseHandler
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class ShopActivity :
    BaseActivity<ShopNavigation, ShopViewModel, ActivityShopBinding>(ActivityShopBinding::inflate) {

    override val viewModel: ShopViewModel by viewModel()

    override val navigation: ShopNavigation by inject()
    private val loaderNavigation: LoaderNavigation by inject()

    private val itemList = mutableListOf<Item>()
    private var state: LoadingStateDelegate? = null

    private val purchaseHandler = PurchaseHandler {
        if (it.isSuccess) {
            navigation.navigateLoader(loaderNavigation)
        } else {
            showErrorAlert(
                message = it.exception?.message.toString(),
                positiveAction = { },
                negativeAction = { finish() }
            )
        }
    }
    private val billingClientWrapper: BillingClientWrapper by inject()

    override fun initView() {
        initBilling()
        initState()
        initToolbar()
        initSubBtn()
        initRestoreBtn()
        initTexts()
    }

    private fun initBilling() {
        billingClientWrapper.setup(this, purchaseHandler)
        getPricesSubs()
    }

    private fun initState() = with(binding) {
        state = LoadingStateDelegate(container, circleCenter).apply {
            TransitionManager.beginDelayedTransition(root, AutoTransition())
            showLoading()
        }
    }

    private fun initToolbar() = with(binding) {
        toolBar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    private fun initSubBtn() = with(binding.btnSub) {
        setOnClickListener {
            if (viewModel.isSub) {
                when (val sku = viewModel.sku) {
                    BillingClientWrapper.Product.WEEKLY -> showCancelDialog(sku.sku)
                    BillingClientWrapper.Product.MONTHLY -> showCancelDialog(sku.sku)
                    BillingClientWrapper.Product.LIFETIME -> showCancelDialogPermanent()
                    else -> showCancelDialog("")
                }
            } else {
                SubDialog.newInstance(itemList).show(supportFragmentManager, SubDialog.KEY)
            }
        }
    }

    private fun initRestoreBtn() = with(binding.btnRestore) {
        setOnClickListener {
            showRestoreDialog()
        }
    }

    private fun initTexts() = with(binding) {
        if (viewModel.isSub) {
            tvTitle.text = "Підписку оформлено"
            tvDescription.text = "У тебе є доступ до\nусього контенту"
            btnSub.text = "Скасувати підписку"
            konfettiView.start(Party.rain)
        } else {
            tvTitle.text = "Оформити підписку"
            tvDescription.text = "Підтримай українське \uD83C\uDDFA\uD83C\uDDE6"
            btnSub.text = "Оформити підписку"
            konfettiView.stop(Party.rain)
        }
    }

    private fun showRestoreDialog() {
        showAlert(
            title = "Відновити покупки",
            message = "Переконайся, що ти використовуєш свій платіжний обліковий запис для відновлення покупок",
            positiveButton = "Відновити",
            negativeButton = "Підтримка",
            positiveAction = {
                navigation.navigateLoader(loaderNavigation)
            },
            negativeAction = {
                sendEmail(
                    subject = "Відновлення покупок",
                    text = "Відправлено з додатку Дімка"
                )
            }
        )
    }

    private fun showCancelDialogPermanent() {
        showAlert(
            title = "Підписка",
            message = "У тебе оформлений одноразовий платіж, ти отримав Преміум-акаунт назавжди.\n\nТи не можеш скасувати підписку, тому що кошти не списуються",
            positiveButton = "Закрити",
            negativeButton = "Підтримка",
            positiveAction = {},
            negativeAction = {
                sendEmail(
                    subject = "Скасування підписки",
                    text = ""
                )
            }
        )
    }

    private fun showCancelDialog(sku: String) {
        showAlert(
            title = "Ти впевнений?",
            message = "Ти втратиш доступ до всього Преміум-контенту",
            positiveButton = "Скасувати",
            negativeButton = "Закрити",
            positiveAction = {
                openSubscriptions(sku)
            },
            negativeAction = {}
        )
    }

    override fun initObs() {
        supportFragmentManager.setFragmentResultListener(
            SubDialog.KEY_RESULT,
            this@ShopActivity
        ) { _, bundle ->
            val result: String = bundle.getString(SubDialog.KEY_RESULT)!!
            BillingClientWrapper.Product.fromSku(result)?.let { buyItem(it) }
        }
    }

    private fun getPricesSubs() {
        billingClientWrapper.getPurchasesList { r ->
            r.fold(
                onSuccess = {
                    itemList.addAll(it)
                    TransitionManager.beginDelayedTransition(binding.root, AutoTransition())
                    state?.showContent()
                },
                onFailure = {
                    showErrorAlert(
                        message = it.message.orEmpty(),
                        positiveAction = { getPricesSubs() },
                        negativeAction = { finish() }
                    )
                }
            )
        }
    }

    private fun buyItem(sku: BillingClientWrapper.Product) {
        billingClientWrapper.purchase(sku) { r ->
            r.fold(
                onSuccess = {},
                onFailure = {
                    showErrorAlert(
                        message = it.message.orEmpty(),
                        positiveAction = { buyItem(sku) },
                        negativeAction = { finish() }
                    )
                }
            )
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (billingClientWrapper.billingProcessor?.onActivityResult(
                requestCode,
                resultCode,
                data
            ) == true
        ) {
            return
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun clear() {
        billingClientWrapper.destroy()
    }
}
