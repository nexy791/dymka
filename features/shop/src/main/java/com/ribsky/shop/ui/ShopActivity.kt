package com.ribsky.shop.ui

import android.content.Intent
import androidx.core.view.isGone
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.transition.AutoTransition
import androidx.transition.TransitionManager
import coil.load
import com.redmadrobot.lib.sd.LoadingStateDelegate
import com.ribsky.analytics.Analytics
import com.ribsky.billing.wrapper.BillingClientWrapper
import com.ribsky.common.base.BaseActivity
import com.ribsky.common.utils.ext.ActionExt.Companion.openSubscriptions
import com.ribsky.common.utils.ext.ActionExt.Companion.sendEmail
import com.ribsky.common.utils.ext.AlertsExt.Companion.showAlert
import com.ribsky.common.utils.ext.ViewExt.Companion.showBottomSheetDialog
import com.ribsky.common.utils.party.Party
import com.ribsky.core.Resource
import com.ribsky.dialogs.factory.cats.CatsFactory
import com.ribsky.dialogs.factory.error.ErrorFactory.Companion.showErrorDialog
import com.ribsky.domain.model.top.BaseTopModel
import com.ribsky.navigation.features.LoaderNavigation
import com.ribsky.navigation.features.ShareStoryNavigation
import com.ribsky.navigation.features.ShopNavigation
import com.ribsky.shop.adapter.cats.CatsAdapter
import com.ribsky.shop.adapter.more.CatsMoreAdapter
import com.ribsky.shop.databinding.ActivityShopBinding
import com.ribsky.shop.dialogs.sub.SubDialog
import jp.alessandro.android.iab.Item
import jp.alessandro.android.iab.handler.PurchaseHandler
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class ShopActivity :
    BaseActivity<ShopViewModel, ActivityShopBinding>(ActivityShopBinding::inflate) {

    override val viewModel: ShopViewModel by viewModel()

    private val shareNavigation: ShareStoryNavigation by inject()
    private val loaderNavigation: LoaderNavigation by inject()

    private val analyticEventFrom: Analytics.Event by lazy {
        intent.getParcelableExtra(ShopNavigation.PARAM) ?: Analytics.Event.PREMIUM_BUY_FROM_UNKNOWN
    }

    private val itemList = mutableListOf<Item>()
    private var state: LoadingStateDelegate? = null

    private val purchaseHandler = PurchaseHandler {
        if (it.isSuccess) {
            Analytics.logEvent(analyticEventFrom)
            loaderNavigation.navigate(this)
        } else {
            showErrorDialog(it.exception?.localizedMessage) { finish() }
        }
    }
    private val billingClientWrapper: BillingClientWrapper by inject()

    private var concatAdapter: ConcatAdapter? = null
    private var adapterCats: CatsAdapter? = null
    private var adapterMore: CatsMoreAdapter? = null

    private val callback: SubDialog.Callback = object : SubDialog.Callback {

        override fun onResult(product: BillingClientWrapper.Product) {
            buyItem(product)
        }

        override fun onDiscount() {
            shareNavigation.navigate(this@ShopActivity)
        }
    }

    override fun initView() {
        Analytics.logEvent(Analytics.Event.PREMIUM_OPEN)
        initState()
        initToolbar()
        initShare()
        initSubBtn()
        initRestoreBtn()
        initTexts()
        initHeartAnimation()
        initTop()
    }

    private fun initTop() {
        adapterCats = CatsAdapter {
            showBottomSheetDialog(CatsFactory().createDialog())
        }
        adapterMore = CatsMoreAdapter {
            showBottomSheetDialog(CatsFactory().createDialog())
        }
        concatAdapter = ConcatAdapter(adapterCats, adapterMore)
        binding.rvCats.apply {
            layoutManager =
                LinearLayoutManager(this@ShopActivity, LinearLayoutManager.HORIZONTAL, false)
            adapter = concatAdapter
        }
    }

    private fun initHeartAnimation() {
        binding.ivHeart.apply {
            animate().scaleX(1.2f).scaleY(1.2f).setDuration(1000).withEndAction {
                animate().scaleX(1f).scaleY(1f).setDuration(1000).withEndAction {
                    initHeartAnimation()
                }
            }
        }
    }

    private fun initDiscount() = with(binding) {
        cardDiscount.isGone = !viewModel.isDiscount
        cardShare.isGone = viewModel.isDiscount
        if (viewModel.isSub) {
            cardShare.isGone = true
            cardDiscount.isGone = true
        }
        btnSub23.setOnClickListener {
            showBottomSheetDialog(
                SubDialog.newInstance(viewModel.isDiscount, itemList, callback),
            )
        }
        if (viewModel.isDiscount) {
            tvDescPrice2.text =
                "Вітаємо! Ти маєш знижку на підписку ${viewModel.discountStatus.value!!.data!!}"
        }

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

    private fun initShare() = with(binding) {
        cardShare.setOnClickListener {
            shareNavigation.navigate(this@ShopActivity)
        }
    }

    private fun initSubBtn() = with(binding.btnSub) {
        setOnClickListener {
            if (viewModel.isSub) {
                val weeklyList = listOf(
                    BillingClientWrapper.Product.WEEKLY,
                    BillingClientWrapper.Product.WEEKLY_LITE,
                    BillingClientWrapper.Product.WEEKLY_FULL
                )
                val monthlyList = listOf(
                    BillingClientWrapper.Product.MONTHLY,
                    BillingClientWrapper.Product.MONTHLY_LITE,
                    BillingClientWrapper.Product.MONTHLY_FULL
                )
                val yearList = listOf(
                    BillingClientWrapper.Product.YEARLY_LITE,
                    BillingClientWrapper.Product.YEARLY_FULL
                )
                val lifetimeList = listOf(
                    BillingClientWrapper.Product.LIFETIME,
                    BillingClientWrapper.Product.LIFETIME_LITE,
                    BillingClientWrapper.Product.LIFETIME_FULL
                )
                when (val sku = viewModel.sku) {
                    in weeklyList -> sku?.sku?.let { showCancelDialog(it) } ?: showCancelDialog("")
                    in monthlyList -> sku?.sku?.let { showCancelDialog(it) } ?: showCancelDialog("")
                    in yearList -> sku?.sku?.let { showCancelDialog(it) } ?: showCancelDialog("")
                    in lifetimeList -> showCancelDialogPermanent()
                    else -> showCancelDialog("")
                }
            } else {
                showBottomSheetDialog(
                    SubDialog.newInstance(viewModel.isDiscount, itemList, callback)
                )
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
                loaderNavigation.navigate(this@ShopActivity)
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

    override fun initObs() = with(viewModel) {
        getUser()
        discountStatus.observe(this@ShopActivity) {
            when (it.status) {
                Resource.Status.LOADING -> {}
                Resource.Status.SUCCESS -> {
                    initBilling()
                    initDiscount()
                }

                Resource.Status.ERROR -> {
                    initBilling()
                    initDiscount()
                }
            }
        }
        userStatus.observe(this@ShopActivity) {
            when (it.status) {
                Resource.Status.LOADING -> {}
                Resource.Status.SUCCESS -> {
                    updateProfileImage(it.data?.image)
                    getIsFreeDiscountAvailable()
                    getTop()
                }

                Resource.Status.ERROR -> showErrorDialog(it.exception?.localizedMessage) { finish() }
            }
        }
        topStatus.observe(this@ShopActivity) {
            when (it.status) {
                Resource.Status.LOADING -> {}
                Resource.Status.SUCCESS -> updateTop(it.data!!)
                Resource.Status.ERROR -> {}
            }
        }
    }

    private fun updateTop(data: List<BaseTopModel>) {
        adapterCats?.submitList(data)
        adapterMore?.submitList(listOf(Unit))
    }

    private fun updateProfileImage(image: String?) {
        image?.let {
            binding.imageAvatar.load(it)
        }
    }

    private fun getPricesSubs() {
        billingClientWrapper.getPurchasesList() { r ->
            r.fold(
                onSuccess = {
                    itemList.addAll(it)
                    TransitionManager.beginDelayedTransition(binding.root, AutoTransition())
                    state?.showContent()
                },
                onFailure = {
                    showErrorDialog(it.localizedMessage) { finish() }
                }
            )
        }
    }

    private fun buyItem(sku: BillingClientWrapper.Product) {
        billingClientWrapper.purchase(sku) { r ->
            r.fold(
                onSuccess = {},
                onFailure = {
                    showErrorDialog(it.localizedMessage) { finish() }
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
