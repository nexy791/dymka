package com.ribsky.shop.dialogs.sub

import android.annotation.SuppressLint
import android.text.method.LinkMovementMethod
import androidx.core.text.parseAsHtml
import androidx.core.view.isGone
import com.revenuecat.purchases.models.StoreProduct
import com.revenuecat.purchases.models.googleProduct
import com.ribsky.billing.wrapper.BillingClientWrapper
import com.ribsky.common.base.BaseSheet
import com.ribsky.common.utils.party.Party
import com.ribsky.shop.databinding.DialogSubBinding

class SubDialog(
    private val callback: Callback? = null,
    private val listItems: List<StoreProduct> = listOf(),
    private val isDiscount: Boolean = false,
) : BaseSheet<DialogSubBinding>(DialogSubBinding::inflate) {

    interface Callback {
        fun onResult(product: StoreProduct)
        fun onDiscount()
    }

    @SuppressLint("SetTextI18n")
    override fun initViews() = with(binding) {
        if (callback == null) dismiss()
        initParty()
        initBtns()
        initTexts()
    }

    private fun initParty() {
        binding.konfettiView.start(Party.rain)
    }

    private fun initBtns() = with(binding) {
        val weekSub =
            if (isDiscount) BillingClientWrapper.Product.WEEKLY_LITE else BillingClientWrapper.Product.WEEKLY_FULL
        val monthSub =
            if (isDiscount) BillingClientWrapper.Product.MONTHLY_LITE else BillingClientWrapper.Product.MONTHLY_FULL
        val lifetimeSub =
            if (isDiscount) BillingClientWrapper.Product.LIFETIME_LITE else BillingClientWrapper.Product.LIFETIME_FULL
        val yearSub =
            if (isDiscount) BillingClientWrapper.Product.YEARLY_LITE else BillingClientWrapper.Product.YEARLY_FULL

        cardWeek.setOnClickListener {
            listItems.find { it.googleProduct?.productId == weekSub.sku }?.let { it1 -> setResultAndDismiss(it1) }
        }
        cardMonth.setOnClickListener {
            listItems.find { it.googleProduct?.productId == monthSub.sku }?.let { it1 -> setResultAndDismiss(it1) }
        }
        cardLifetime.setOnClickListener {
            listItems.find { it.googleProduct?.productId == lifetimeSub.sku }?.let { it1 -> setResultAndDismiss(it1) }
        }
        cardYear.setOnClickListener {
            listItems.find { it.googleProduct?.productId == yearSub.sku }?.let { it1 -> setResultAndDismiss(it1) }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun initTexts() = with(binding) {
        btnSub.isGone = isDiscount
        btnSub.setOnClickListener {
            callback?.onDiscount()
            dismiss()
        }
        textDescription.apply {
            movementMethod = LinkMovementMethod.getInstance()
            text = """
Скасування підписки у будь-який момент<br>
При покупці підписки «Назавжди» твої кошти списуються лише один раз<br><br>
<a href="https://dymka.me/privacy.html">Політика конфіденційності</a><br><a href="https://dymka.me/rules.html">Правила користування</a>
            """.trimIndent().parseAsHtml()
        }


        val lifeTimePrice = listItems.firstOrNull {
            if (isDiscount) {
                it.googleProduct?.productId == BillingClientWrapper.Product.LIFETIME_LITE.sku
            } else {
                it.googleProduct?.productId == BillingClientWrapper.Product.LIFETIME_FULL.sku
            }
        }?.price?.formatted ?: ""

        val yearPrice = listItems.firstOrNull {
            if (isDiscount) {
                it.googleProduct?.productId == BillingClientWrapper.Product.YEARLY_LITE.sku
            } else {
                it.googleProduct?.productId == BillingClientWrapper.Product.YEARLY_FULL.sku
            }
        }?.price?.formatted ?: ""

        val monthPrice = listItems.firstOrNull {
            if (isDiscount) {
                it.googleProduct?.productId == BillingClientWrapper.Product.MONTHLY_LITE.sku
            } else {
                it.googleProduct?.productId == BillingClientWrapper.Product.MONTHLY_FULL.sku
            }
        }?.price?.formatted ?: ""

        val weekPrice = listItems.firstOrNull {
            if (isDiscount) {
                it.googleProduct?.productId == BillingClientWrapper.Product.WEEKLY_LITE.sku
            } else {
                it.googleProduct?.productId == BillingClientWrapper.Product.WEEKLY_FULL.sku
            }
        }?.price?.formatted ?: ""

        val lifeTimeDiscountSuffix =
            if (isDiscount) "<s>" + listItems.firstOrNull {
                it.googleProduct?.productId == BillingClientWrapper.Product.LIFETIME_FULL.sku
            }?.price?.formatted + "</s>" else ""

        val yearDiscountSuffix =
            if (isDiscount) "<s>" + listItems.firstOrNull {
                it.googleProduct?.productId == BillingClientWrapper.Product.YEARLY_FULL.sku
            }?.price?.formatted + "</s>" else ""

        val monthDiscountSuffix =
            if (isDiscount) "<s>" + listItems.firstOrNull {
                it.googleProduct?.productId == BillingClientWrapper.Product.MONTHLY_FULL.sku
            }?.price?.formatted + "</s>" else ""

        val weekDiscountSuffix =
            if (isDiscount) "<s>" + listItems.firstOrNull {
                it.googleProduct?.productId == BillingClientWrapper.Product.WEEKLY_FULL.sku
            }?.price?.formatted + "</s>" else ""

        tvLifetime.text = "$lifeTimeDiscountSuffix $lifeTimePrice один раз".parseAsHtml()
        tvYear.text = "$yearDiscountSuffix $yearPrice на рік".parseAsHtml()
        tvMonth.text = "$monthDiscountSuffix $monthPrice на місяць".parseAsHtml()
        tvWeek.text = "$weekDiscountSuffix $weekPrice на тиждень".parseAsHtml()

    }

    private fun setResultAndDismiss(product: StoreProduct) {
        callback?.onResult(product)
        dismiss()
    }

    override fun clear() {
    }

    companion object {
        fun newInstance(
            isDiscount: Boolean,
            list: List<StoreProduct>,
            callback: Callback,
        ) = SubDialog(callback, list, isDiscount)
    }

    override fun initObserves() {
    }
}
