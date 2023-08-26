package com.ribsky.paywall.dialogs

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.ribsky.analytics.Analytics
import com.ribsky.common.R
import com.ribsky.common.alias.commonRaw
import com.ribsky.common.base.BaseSheetFullScreen
import com.ribsky.common.utils.ext.ViewExt.Companion.showBottomSheetDialog
import com.ribsky.common.utils.party.Party
import com.ribsky.common.utils.sound.SoundHelper.playSound
import com.ribsky.core.Resource
import com.ribsky.dialogs.factory.cats.CatsFactory
import com.ribsky.domain.model.top.BaseTopModel
import com.ribsky.navigation.features.PayWallNavigation
import com.ribsky.paywall.adapter.cats.CatsAdapter
import com.ribsky.paywall.adapter.more.CatsMoreAdapter
import com.ribsky.paywall.databinding.DialogPaywallBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class PayWallDialog(
    private val date: String = "",
    private val callback: PayWallNavigation.Callback? = null,
) : BaseSheetFullScreen<DialogPaywallBinding>(DialogPaywallBinding::inflate) {

    private val viewModel: PayWallViewModel by viewModel()

    override fun getTheme(): Int = R.style.AppPayWallBottomSheetDialogTheme

    private var concatAdapter: ConcatAdapter? = null
    private var adapterCats: CatsAdapter? = null
    private var adapterMore: CatsMoreAdapter? = null

    @SuppressLint("SetTextI18n")
    override fun initViews() = with(binding) {
        if (callback == null) dismiss()
        Analytics.logEvent(Analytics.Event.PAYWALL_OPEN)
        playSound(commonRaw.sound_ambient)
        konfettiView.start(Party.rain)
        chip2.text = date
        btnGetDiscount.setOnClickListener {
            callback?.onDiscount()
            dismiss()
        }
        tvLater.setOnClickListener {
            dismiss()
        }
        initTop()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return super.onCreateDialog(savedInstanceState).apply {
            dialog?.apply {
                setCanceledOnTouchOutside(false)
                setCancelable(false)
            }
        }
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
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = concatAdapter
        }
    }

    override fun initObserves() = with(viewModel) {
        getTop()
        topStatus.observe(viewLifecycleOwner) {
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


    override fun clear() {
    }

    companion object {

        const val TAG = "PayWallDialog"

        fun newInstance(date: String, callback: PayWallNavigation.Callback) =
            PayWallDialog(date, callback)
    }


}
