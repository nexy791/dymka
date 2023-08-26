package com.ribsky.intro.ui.fragments.from

import androidx.core.os.bundleOf
import com.revenuecat.purchases.Purchases
import com.ribsky.analytics.Analytics
import com.ribsky.common.base.BaseFragment
import com.ribsky.common.utils.chip.ChipBuilder.Companion.createChip
import com.ribsky.intro.databinding.FragmentIntroFromBinding
import com.ribsky.intro.utils.IntroCallback
import org.koin.androidx.viewmodel.ext.android.viewModel

class IntroFromFragment :
    BaseFragment<IntroFromViewModel, FragmentIntroFromBinding>(FragmentIntroFromBinding::inflate) {

    override val viewModel: IntroFromViewModel by viewModel()
    private var introCallback: IntroCallback? = null

    private var selectedId = -1
        set(value) {
            field = value
            binding.btnNext.isEnabled = value != -1
        }

    override fun initView() = with(binding) {
        introCallback = activity as IntroCallback
        initChips()
        btnNext.setOnClickListener {
            Analytics.logEvent(Analytics.Event.INTRO_FROM_PICK, bundleOf("from" to selectedId))
            viewModel.setFrom(selectedId)
            introCallback?.next()
        }
    }

    private fun initChips(): Unit = with(binding.chipGroup) {
        val list = viewModel.getFroms()
        removeAllViews()
        for (i in list.indices) {
            val chip = createChip(
                text = list.map { it.name }[i],
                id = i,
                isChecked = selectedId == i,
                callback = { isChecked ->
                    selectedId = if (isChecked) i else -1
                    initChips()
                }
            )
            addView(chip)
        }
    }

    override fun initObs() {

    }

    override fun clear() {

    }

    companion object {
        fun newInstance() = IntroFromFragment()
    }

}