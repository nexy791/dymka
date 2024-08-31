package com.ribsky.account.dialog.base

import android.graphics.drawable.AnimatedVectorDrawable
import androidx.transition.AutoTransition
import androidx.transition.TransitionManager
import com.redmadrobot.lib.sd.LoadingStateDelegate
import com.ribsky.account.databinding.DialogProfileBinding
import com.ribsky.account.model.UserModel
import com.ribsky.analytics.Analytics
import com.ribsky.common.base.BaseSheetFullScreen
import com.ribsky.common.utils.chip.ChipBuilder.Companion.createChip
import com.ribsky.common.utils.ext.ViewExt.Companion.formatUserName
import com.ribsky.common.utils.glide.ImageLoader.Companion.loadImage
import com.ribsky.core.Resource
import com.ribsky.dialogs.factory.error.ErrorFactory.Companion.showErrorDialog
import com.ribsky.navigation.features.BetaNavigation
import com.ribsky.navigation.features.SettingsNavigation
import com.ribsky.navigation.features.ShopNavigation
import org.koin.android.ext.android.inject


abstract class BaseProfileDialog :
    BaseSheetFullScreen<DialogProfileBinding>(DialogProfileBinding::inflate) {

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
                Resource.Status.SUCCESS -> updateUi(result.data!!)
                Resource.Status.ERROR -> showErrorDialog(result.exception?.localizedMessage) { dismiss() }
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
            shopNavigation.navigate(
                requireContext(),
                ShopNavigation.Params(Analytics.Event.PREMIUM_BUY_FROM_USER)
            )
        }
        btnShop.setOnClickListener {
            Analytics.logEvent(Analytics.Event.PREMIUM_FROM_USER)
            shopNavigation.navigate(
                requireContext(),
                ShopNavigation.Params(Analytics.Event.PREMIUM_BUY_FROM_USER)
            )
        }
        btnSettings.setOnClickListener { settingsNavigation.navigate(requireContext()) }
        cardRate.setOnClickListener { betaNavigation.navigate(requireContext()) }

        (imageView3.drawable as AnimatedVectorDrawable).apply {
            start()
        }
    }

    private fun showContent() {
        TransitionManager.beginDelayedTransition(binding.root, AutoTransition())
        state?.showContent()
    }

    private fun updateUi(data: UserModel) = with(binding.container) {
        updateInfo(data)
        updatePremInfo(data.isPrem)
        updateChips(data.bio)
        updateTestInfo(data.books)
        updateLessonsInfo(data.lessons)
        updateStars(data.stars)
        updateDaysInfo(data.streak.day, data.streak.isStreak)
        showContent()
    }

    private fun updateInfo(data: UserModel) = with(binding.container) {
        ivAccount.loadImage(data.image)
        tvName.text = data.name.formatUserName(data.isPrem)
        tvEmail.text = data.email
    }

    private fun updateChips(list: List<String>) {
        binding.container.chipGroup.removeAllViews()
        list.forEach {
            val chip = createChip(
                text = it,
                id = it.hashCode(),
                size = 14f,
                checkable = false,
                isChecked = false,
                callback = {}
            )
            binding.container.chipGroup.addView(chip)
        }
    }

    abstract fun updateDaysInfo(days: Int, isChecked: Boolean)

    abstract fun updatePremInfo(isPrem: Boolean)

    private fun updateTestInfo(books: Int) = with(binding.container) {
        tvBooks.text = books.toString()
        tvTestCard.text = "$books/∞"
    }

    private fun updateLessonsInfo(lessonInfo: UserModel.LessonModel) = with(binding.container) {
        cpbLessons.setProgressWithAnimation(lessonInfo.calculatePercent().toFloat())
        tvLessons.text = "${lessonInfo.calculatePercent()}%"
        tvLessonCard.text = "${lessonInfo.count}/${lessonInfo.lessons}"
    }

    private fun updateStars(stars: UserModel.StarsModel) = with(binding.container) {
        tvStars.text = "${stars.count}/${stars.stars}"
    }


    override fun clear() {
        state = null
    }
}
