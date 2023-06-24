package com.ribsky.intro.ui.fragments.goal

import androidx.core.os.bundleOf
import com.ribsky.analytics.Analytics
import com.ribsky.common.base.BaseFragment
import com.ribsky.common.utils.chip.ChipBuilder.Companion.createChip
import com.ribsky.intro.databinding.FragmentIntroGoalBinding
import com.ribsky.intro.utils.IntroCallback
import org.koin.androidx.viewmodel.ext.android.viewModel

class IntroGoalFragment :
    BaseFragment<IntroGoalViewModel, FragmentIntroGoalBinding>(FragmentIntroGoalBinding::inflate) {

    override val viewModel: IntroGoalViewModel by viewModel()

    private var selectedId = -1
        set(value) {
            field = value
            binding.btnNext.isEnabled = value != -1
        }

    private var introCallback: IntroCallback? = null

    override fun initView() = with(binding) {
        introCallback = activity as IntroCallback
        initChips()
        btnNext.setOnClickListener {
            Analytics.logEvent(Analytics.Event.INTRO_GOAL_PICK, bundleOf("goal" to selectedId))
            viewModel.setGoal(selectedId)
            introCallback?.next()
        }
    }

    private fun initChips(): Unit = with(binding.chipGroup) {
        val list = viewModel.getGoals()
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
        fun newInstance() = IntroGoalFragment()
    }

}