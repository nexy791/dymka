package com.ribsky.shop.dialogs.sub

import android.annotation.SuppressLint
import android.text.method.LinkMovementMethod
import androidx.core.os.bundleOf
import androidx.core.text.parseAsHtml
import androidx.fragment.app.setFragmentResult
import com.ribsky.billing.wrapper.BillingClientWrapper
import com.ribsky.common.base.BaseSheet
import com.ribsky.common.utils.party.Party
import com.ribsky.shop.databinding.DialogSubBinding
import jp.alessandro.android.iab.Item

// TODO: refactor
class SubDialog : BaseSheet<DialogSubBinding>(DialogSubBinding::inflate) {

    private val listItems: List<Item> by lazy {
        requireArguments().getParcelableArrayList(KEY) ?: emptyList()
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
        cardWeek.setOnClickListener {
            setResultAndDismiss(BillingClientWrapper.Product.WEEKLY)
        }
        cardMonth.setOnClickListener {
            setResultAndDismiss(BillingClientWrapper.Product.MONTHLY)
        }
        cardLifetime.setOnClickListener {
            setResultAndDismiss(BillingClientWrapper.Product.LIFETIME)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun initTexts() = with(binding) {
        textDescription.apply {
            movementMethod = LinkMovementMethod.getInstance()
            text = """
Скасування підписки у будь-який момент<br>
При покупці підписки «Назавжди» твої кошти списуються лише один раз<br><br>
<a href="https://dymka.me/privacy.html">Політика конфіденційності</a><br><a href="https://dymka.me/rules.html">Правила користування</a>
            """.trimIndent().parseAsHtml()
        }
        tvLifetime.text =
            listItems.first { it.sku == BillingClientWrapper.Product.LIFETIME.sku }.price + " один раз"
        tvMonth.text =
            listItems.first { it.sku == BillingClientWrapper.Product.MONTHLY.sku }.price + " на місяць"
        tvWeek.text =
            listItems.first { it.sku == BillingClientWrapper.Product.WEEKLY.sku }.price + " на тиждень"
    }

    private fun setResultAndDismiss(product: BillingClientWrapper.Product) {
        setFragmentResult(
            KEY_RESULT,
            bundleOf(KEY_RESULT to product.sku)
        )
        dismiss()
    }

    override fun clear() {
    }

    companion object {
        const val KEY = "SUB_KEY"
        const val KEY_RESULT = "SUB_KEY_RESULT"

        fun newInstance(list: List<Item>) = SubDialog().apply {
            arguments = bundleOf(KEY to list)
        }

    }

    override fun initObserves() {
    }
}
