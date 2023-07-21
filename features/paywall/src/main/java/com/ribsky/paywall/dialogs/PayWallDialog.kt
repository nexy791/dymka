package com.ribsky.paywall.dialogs

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import com.ribsky.analytics.Analytics
import com.ribsky.common.R
import com.ribsky.common.alias.commonRaw
import com.ribsky.common.base.BaseSheetFullScreen
import com.ribsky.common.utils.party.Party
import com.ribsky.common.utils.sound.SoundHelper.playSound
import com.ribsky.navigation.features.PayWallNavigation
import com.ribsky.paywall.databinding.DialogPaywallBinding

class PayWallDialog(
    private val date: String = "",
    private val callback: PayWallNavigation.Callback,
) : BaseSheetFullScreen<DialogPaywallBinding>(DialogPaywallBinding::inflate) {

    override fun getTheme(): Int = R.style.AppPayWallBottomSheetDialogTheme


    @SuppressLint("SetTextI18n")
    override fun initViews() = with(binding) {
        Analytics.logEvent(Analytics.Event.PAYWALL_OPEN)
        playSound(commonRaw.sound_ambient)
        konfettiView.start(Party.rain)
        chip2.text = date
        btnGetDiscount.setOnClickListener {
            callback.onDiscount()
            dismiss()
        }
        tvLater.setOnClickListener {
            dismiss()
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return super.onCreateDialog(savedInstanceState).apply {
            dialog?.apply {
                setCanceledOnTouchOutside(false)
                setCancelable(false)
            }
        }
    }


    override fun clear() {
    }

    companion object {

        const val TAG = "PayWallDialog"

        fun newInstance(date: String, callback: PayWallNavigation.Callback) =
            PayWallDialog(date, callback)
    }

    override fun initObserves() {
    }
}
