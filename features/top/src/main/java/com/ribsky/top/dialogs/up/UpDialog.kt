package com.ribsky.top.dialogs.up

import android.content.DialogInterface
import androidx.core.view.isGone
import com.ribsky.analytics.Analytics
import com.ribsky.common.alias.commonDrawable
import com.ribsky.common.alias.commonRaw
import com.ribsky.common.base.BaseSheet
import com.ribsky.common.utils.ext.ViewExt.Companion.formatUserName
import com.ribsky.common.utils.ext.ViewExt.Companion.vibrate
import com.ribsky.common.utils.party.Party
import com.ribsky.common.utils.sound.SoundHelper.playSound
import com.ribsky.navigation.features.TopDialogsNavigation
import com.ribsky.top.databinding.DialogUpBinding
import com.ribsky.top.utils.AnimExt.Companion.swapAnimation
import com.ribsky.top.view.LayoutTopView
import com.ribsky.top.view.LayoutTopViewMe

class UpDialog(
    private val type: TopDialogsNavigation.Type? = null,
    private val users: List<TopDialogsNavigation.UserModel> = listOf(),
    private val onDismiss: () -> Unit = {},
) : BaseSheet<DialogUpBinding>(DialogUpBinding::inflate) {

    override fun initViews() {
        if (type == null) {
            dismiss()
            return
        }
        binding.konfettiView.start(Party.rain)
        initBtns()
        updateUi(users)
        type.let {
            Analytics.logEvent(
                when (it) {
                    TopDialogsNavigation.Type.STARS -> Analytics.Event.TOP_UP_STARS
                    TopDialogsNavigation.Type.TESTS -> Analytics.Event.TOP_UP_TESTS
                }
            )
        }
    }

    private fun initAnim() = with(binding) {
        root.postDelayed({
            swapAnimation(layoutTopView, layoutTopView4)
            vibrate()
            playSound(commonRaw.sound_up)
        }, 1000)

    }

    private fun initBtns() = with(binding) {
        btnNext.setOnClickListener {
            dismiss()
        }
    }

    private fun updateUi(list: List<TopDialogsNavigation.UserModel>) = with(binding) {
        if (list.isEmpty()) {
            dismiss()
            return@with
        }
        if (list.size < 3) binding.layoutTopView3.isGone = true
        list.forEachIndexed { index, baseTopModel ->
            when (index) {
                0 -> updateUiViewMe(layoutTopView4, baseTopModel)
                1 -> updateUiView(layoutTopView, baseTopModel)
                2 -> updateUiView(layoutTopView3, baseTopModel)
            }
        }
        initAnim()
    }

    private fun updateUiView(view: LayoutTopView, user: TopDialogsNavigation.UserModel) =
        with(view) {
            setUsername(user.name.formatUserName(user.hasPrem))
            setPhoto(user.avatar)
            setScore(user.score)
            setScoreDrawable(
                when (type) {
                    TopDialogsNavigation.Type.STARS -> commonDrawable.ic_round_star_border_24
                    TopDialogsNavigation.Type.TESTS -> commonDrawable.ic_outline_collections_bookmark_24
                    else -> commonDrawable.ic_round_star_border_24
                }
            )
        }

    private fun updateUiViewMe(view: LayoutTopViewMe, user: TopDialogsNavigation.UserModel) =
        with(view) {
            setUsername(user.name.formatUserName(user.hasPrem))
            setPhoto(user.avatar)
            setScore(user.score)
            setScoreDrawable(
                when (type) {
                    TopDialogsNavigation.Type.STARS -> commonDrawable.ic_round_star_border_24
                    TopDialogsNavigation.Type.TESTS -> commonDrawable.ic_outline_collections_bookmark_24
                    else -> commonDrawable.ic_round_star_border_24
                }
            )
        }

    override fun initObserves() {}

    override fun clear() {}

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        onDismiss()
    }

    companion object {

        fun newInstance(
            type: TopDialogsNavigation.Type,
            users: List<TopDialogsNavigation.UserModel>,
            onDismiss: () -> Unit,
        ) =
            UpDialog(type, users, onDismiss)
    }
}
