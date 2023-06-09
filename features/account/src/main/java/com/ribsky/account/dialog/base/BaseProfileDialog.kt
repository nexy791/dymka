package com.ribsky.account.dialog.base

import androidx.navigation.fragment.findNavController
import androidx.transition.AutoTransition
import androidx.transition.TransitionManager
import coil.load
import coil.transform.CircleCropTransformation
import com.redmadrobot.lib.sd.LoadingStateDelegate
import com.ribsky.account.databinding.DialogProfileBinding
import com.ribsky.account.model.LessonInfo
import com.ribsky.analytics.Analytics
import com.ribsky.common.base.BaseSheet
import com.ribsky.common.livedata.Resource
import com.ribsky.common.utils.ext.ViewExt.Companion.formatUserName
import com.ribsky.dialogs.factory.error.ErrorFactory.Companion.showErrorDialog
import com.ribsky.domain.model.user.BaseUserModel
import com.ribsky.navigation.features.BetaNavigation
import com.ribsky.navigation.features.SettingsNavigation
import com.ribsky.navigation.features.ShopNavigation
import org.koin.android.ext.android.inject

abstract class BaseProfileDialog : BaseSheet<DialogProfileBinding>(DialogProfileBinding::inflate) {

    abstract val viewModel: BaseProfileViewModel

    private val settingsNavigation: SettingsNavigation by inject()
    private val shopNavigation: ShopNavigation by inject()
    private val betaNavigation: BetaNavigation by inject()

    private var state: LoadingStateDelegate? = null

    override fun initViews() {
        initState()
        initBtns()
    }

    override fun initObserves() = with(viewModel) {
        userStatus.observe(viewLifecycleOwner) { result ->
            when (result.status) {
                Resource.Status.LOADING -> {}
                Resource.Status.SUCCESS -> {
                    updateUi(result.data!!)
                    showContent()
                }
                Resource.Status.ERROR -> showErrorDialog(result.exception?.localizedMessage) { dismiss() }
            }
        }
        bookStatus.observe(viewLifecycleOwner) { result ->
            when (result.status) {
                Resource.Status.SUCCESS -> updateTestInfo(result.data!!)
                Resource.Status.LOADING -> {}
                Resource.Status.ERROR -> showErrorDialog(result.exception?.localizedMessage) { dismiss() }
            }
        }
        lessonsStatus.observe(viewLifecycleOwner) { result ->
            when (result.status) {
                Resource.Status.SUCCESS -> updateLessonsInfo(result.data!!)
                Resource.Status.LOADING -> {}
                Resource.Status.ERROR -> showErrorDialog(result.exception?.localizedMessage) { findNavController().navigateUp() }
            }
        }
    }

    private fun initState() = with(binding.container) {
        state = LoadingStateDelegate(container).apply {
            showLoading()
        }
    }

    private fun initBtns() = with(binding.container) {
        binding.fabBack.setOnClickListener { dismiss() }

        cardShop.setOnClickListener {
            Analytics.logEvent(Analytics.Event.PREMIUM_FROM_USER)
            shopNavigation.navigate(requireContext())
        }
        btnShop.setOnClickListener {
            Analytics.logEvent(Analytics.Event.PREMIUM_FROM_USER)
            shopNavigation.navigate(requireContext())
        }
        btnSettings.setOnClickListener { settingsNavigation.navigate(requireContext()) }
        cardRate.setOnClickListener { betaNavigation.navigate(requireContext()) }
    }

    private fun showLoading() {
        TransitionManager.beginDelayedTransition(binding.root, AutoTransition())
        state?.showLoading()
    }

    private fun showContent() {
        TransitionManager.beginDelayedTransition(binding.root, AutoTransition())
        state?.showContent()
    }

    private fun updateUi(data: BaseUserModel) = with(binding.container) {
        ivAccount.load(data.image) {
            transformations(CircleCropTransformation())
        }
        tvName.text = data.name.formatUserName(data.hasPrem)
        tvEmail.text = data.email

        updatePremInfo(data.hasPrem)
    }

    abstract fun updatePremInfo(isPrem: Boolean)

    private fun updateTestInfo(books: Int) = with(binding.container) {
        TransitionManager.beginDelayedTransition(root, AutoTransition())
        tvBooks.text = books.toString()
        btnWords.text = "$books/∞"
    }

    private fun updateLessonsInfo(lessonInfo: LessonInfo) = with(binding.container) {
        TransitionManager.beginDelayedTransition(root, AutoTransition())
        cpbLessons.setProgressWithAnimation(lessonInfo.calculatePercent().toFloat())
        tvLessons.text = "${lessonInfo.calculatePercent()}%"
        btnLessons.text = "${lessonInfo.count}/${lessonInfo.lessons}"
    }

    override fun clear() {
        state = null
    }
}
