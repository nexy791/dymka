package com.ribsky.shop.dialogs.sub

import android.annotation.SuppressLint
import android.text.method.LinkMovementMethod
import androidx.core.os.bundleOf
import androidx.core.text.parseAsHtml
import androidx.core.view.isGone
import com.ribsky.billing.wrapper.BillingClientWrapper
import com.ribsky.common.base.BaseSheet
import com.ribsky.common.utils.party.Party
import com.ribsky.shop.databinding.DialogSubBinding
import jp.alessandro.android.iab.Item

class SubDialog(
    private val callback: Callback,
) : BaseSheet<DialogSubBinding>(DialogSubBinding::inflate) {

    interface Callback {
        fun onResult(product: BillingClientWrapper.Product)
        fun onDiscount()
    }

    private val listItems: List<Item> by lazy {
        requireArguments().getParcelableArrayList(KEY) ?: emptyList()
    }

    private val isDiscount: Boolean by lazy {
        requireArguments().getBoolean(IS_DISCOUNT)
    }

    @SuppressLint("SetTextI18n")
    override fun initViews() = with(binding) {
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
            setResultAndDismiss(weekSub)
        }
        cardMonth.setOnClickListener {
            setResultAndDismiss(monthSub)
        }
        cardLifetime.setOnClickListener {
            setResultAndDismiss(lifetimeSub)
        }
        cardYear.setOnClickListener {
            setResultAndDismiss(yearSub)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun initTexts() = with(binding) {
        btnSub.isGone = isDiscount
        btnSub.setOnClickListener {
            callback.onDiscount()
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
                it.sku == BillingClientWrapper.Product.LIFETIME_LITE.sku
            } else {
                it.sku == BillingClientWrapper.Product.LIFETIME_FULL.sku
            }
        }?.price ?: ""

        val yearPrice = listItems.firstOrNull {
            if (isDiscount) {
                it.sku == BillingClientWrapper.Product.YEARLY_LITE.sku
            } else {
                it.sku == BillingClientWrapper.Product.YEARLY_FULL.sku
            }
        }?.price ?: ""

        val monthPrice = listItems.firstOrNull {
            if (isDiscount) {
                it.sku == BillingClientWrapper.Product.MONTHLY_LITE.sku
            } else {
                it.sku == BillingClientWrapper.Product.MONTHLY_FULL.sku
            }
        }?.price ?: ""

        val weekPrice = listItems.firstOrNull {
            if (isDiscount) {
                it.sku == BillingClientWrapper.Product.WEEKLY_LITE.sku
            } else {
                it.sku == BillingClientWrapper.Product.WEEKLY_FULL.sku
            }
        }?.price ?: ""

        val lifeTimeDiscountSuffix =
            if (isDiscount) "<s>" + listItems.firstOrNull {
                it.sku == BillingClientWrapper.Product.LIFETIME_FULL.sku
            }?.price + "</s>" else ""

        val yearDiscountSuffix =
            if (isDiscount) "<s>" + listItems.firstOrNull {
                it.sku == BillingClientWrapper.Product.YEARLY_FULL.sku
            }?.price + "</s>" else ""

        val monthDiscountSuffix =
            if (isDiscount) "<s>" + listItems.firstOrNull {
                it.sku == BillingClientWrapper.Product.MONTHLY_FULL.sku
            }?.price + "</s>" else ""

        val weekDiscountSuffix =
            if (isDiscount) "<s>" + listItems.firstOrNull {
                it.sku == BillingClientWrapper.Product.WEEKLY_FULL.sku
            }?.price + "</s>" else ""

        tvLifetime.text = "$lifeTimeDiscountSuffix $lifeTimePrice один раз".parseAsHtml()
        tvYear.text = "$yearDiscountSuffix $yearPrice на рік".parseAsHtml()
        tvMonth.text = "$monthDiscountSuffix $monthPrice на місяць".parseAsHtml()
        tvWeek.text = "$weekDiscountSuffix $weekPrice на тиждень".parseAsHtml()

    }

    private fun setResultAndDismiss(product: BillingClientWrapper.Product) {
        callback.onResult(product)
        dismiss()
    }

    override fun clear() {
    }

    companion object {
        const val KEY = "SUB_KEY"
        const val IS_DISCOUNT = "IS_DISCOUNT"
        fun newInstance(
            isDiscount: Boolean,
            list: List<Item>,
            callback: Callback,
        ) = SubDialog(callback).apply {
            arguments = bundleOf(KEY to list, IS_DISCOUNT to isDiscount)
        }
    }

    override fun initObserves() {
    }
}
