package com.ribsky.top.ui.base

import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.transition.AutoTransition
import androidx.transition.TransitionManager
import com.redmadrobot.lib.sd.LoadingStateDelegate
import com.ribsky.common.base.BaseFragment
import com.ribsky.common.livedata.Resource
import com.ribsky.common.utils.ext.TimerExt.Companion.formatTimeDDMMMMHHMM
import com.ribsky.domain.exceptions.Exceptions
import com.ribsky.navigation.features.AccountNavigation
import com.ribsky.navigation.features.TopNavigation
import com.ribsky.top.adapter.bottom.TopBottomAdapter
import com.ribsky.top.adapter.header.TopHeaderAdapter
import com.ribsky.top.adapter.top.TopAdapter
import com.ribsky.top.databinding.FragmentTopRecyclerBinding
import org.koin.android.ext.android.inject

abstract class BaseTopRecyclerFragment<T : BaseTopViewModel>() :
    BaseFragment<TopNavigation, BaseTopViewModel, FragmentTopRecyclerBinding>(
        FragmentTopRecyclerBinding::inflate
    ) {

    override val navigation: TopNavigation by inject()
    private val accountNavigation: AccountNavigation by inject()

    private var state: LoadingStateDelegate? = null
    private var adapterHeader: TopHeaderAdapter? = null
    private var adapterTop: TopAdapter? = null
    private var adapterTopBottom: TopBottomAdapter? = null
    private var concatAdapter: ConcatAdapter? = null

    abstract val viewType: ViewType

    override fun initView(): Unit = with(binding) {
        initState()
        initAdapter()
        initRecycler()
    }

    private fun initState() = with(binding) {
        state = LoadingStateDelegate(recyclerView, progressBar).apply {
            TransitionManager.beginDelayedTransition(binding.root, AutoTransition())
            showLoading()
        }
    }

    private fun initAdapter() {
        adapterTop = TopAdapter() { id ->
            navigation.navigateProfile(accountNavigation, id)
        }
        adapterHeader = TopHeaderAdapter(viewType, formatTimeDDMMMMHHMM(viewModel.time)) {
            navigation.navigateAccount(accountNavigation)
        }
        adapterTopBottom = TopBottomAdapter()
        concatAdapter = ConcatAdapter(adapterHeader, adapterTop, adapterTopBottom)
    }

    private fun initRecycler() = with(binding) {
        recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = concatAdapter
        }
    }

    override fun initObs() = with(viewModel) {
        loadUsers()
        userStatus.observe(viewLifecycleOwner) {
            when (it.status) {
                Resource.Status.LOADING -> {
                    TransitionManager.beginDelayedTransition(binding.root, AutoTransition())
                    state?.showLoading()
                }
                Resource.Status.SUCCESS -> {
                    TransitionManager.beginDelayedTransition(binding.root, AutoTransition())
                    adapterHeader?.submitList(listOf(it.data!!))
                    state?.showContent()
                }
                Resource.Status.ERROR -> {
                    when (val ex = it.exception) {
                        is Exceptions.AuthException -> {
                        }
                        else -> TODO()
                    }
                }
            }
        }

        usersStatus.observe(viewLifecycleOwner) {
            when (it.status) {
                Resource.Status.LOADING -> {
                    TransitionManager.beginDelayedTransition(binding.root, AutoTransition())
                    state?.showLoading()
                }
                Resource.Status.SUCCESS -> {
                    adapterTop?.submitList(it.data)
                    adapterTopBottom?.submitList(listOf(Unit))
                    getUser()
                }
                Resource.Status.ERROR -> {
                }
            }
        }
    }

    override fun clear() {
        state = null
        adapterHeader = null
        adapterTop = null
        adapterTopBottom = null
        concatAdapter = null
    }
}
