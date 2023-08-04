package com.ribsky.top.ui.base

import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.ribsky.common.base.BaseFragment
import com.ribsky.common.utils.ext.ViewExt.Companion.hide
import com.ribsky.common.utils.ext.ViewExt.Companion.show
import com.ribsky.core.Resource
import com.ribsky.core.utils.DateUtils.Companion.formatDateDDMMMMHHMM
import com.ribsky.dialogs.factory.error.ErrorFactory.Companion.showErrorDialog
import com.ribsky.navigation.features.AccountNavigation
import com.ribsky.navigation.features.ProfileNavigation
import com.ribsky.top.adapter.bottom.TopBottomAdapter
import com.ribsky.top.adapter.header.TopHeaderAdapter
import com.ribsky.top.adapter.top.TopAdapter
import com.ribsky.top.databinding.FragmentTopRecyclerBinding
import org.koin.android.ext.android.inject

abstract class BaseTopRecyclerFragment<T : BaseTopViewModel>() :
    BaseFragment<BaseTopViewModel, FragmentTopRecyclerBinding>(
        FragmentTopRecyclerBinding::inflate
    ) {

    private val accountNavigation: AccountNavigation by inject()
    private val profileNavigation: ProfileNavigation by inject()

    private var adapterHeader: TopHeaderAdapter? = null
    private var adapterTop: TopAdapter? = null
    private var adapterTopBottom: TopBottomAdapter? = null
    private var concatAdapter: ConcatAdapter? = null

    abstract val viewType: ViewType

    override fun initView(): Unit = with(binding) {
        initAdapter()
        initRecycler()
    }

    private fun initAdapter() {
        adapterTop = TopAdapter() { id ->
            profileNavigation.navigate(findNavController(), ProfileNavigation.Params(id))
        }
        adapterHeader = TopHeaderAdapter(viewType, formatDateDDMMMMHHMM(viewModel.time)) {
            accountNavigation.navigate(findNavController())
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
        usersStatus.observe(viewLifecycleOwner) {
            when (it.status) {
                Resource.Status.LOADING -> loadContent()
                Resource.Status.SUCCESS -> {
                    adapterTopBottom?.submitList(listOf(Unit))
                    adapterTop?.submitList(it.data) {
                        getUser()
                    }
                }

                Resource.Status.ERROR -> showErrorDialog(it.exception?.localizedMessage) { findNavController().navigateUp() }
            }
        }
        userStatus.observe(viewLifecycleOwner) {
            when (it.status) {
                Resource.Status.LOADING -> {}
                Resource.Status.SUCCESS -> {
                    adapterHeader?.submitList(listOf(it.data!!)) {
                        showContent()
                    }
                }

                Resource.Status.ERROR -> showErrorDialog(it.exception?.localizedMessage) { findNavController().navigateUp() }
            }
        }
    }


    private fun loadContent() {
        binding.recyclerView.hide()
        binding.placeholder.root.apply {
            startShimmer()
            show()
        }
    }

    private fun showContent() {
        binding.placeholder.root.apply {
            stopShimmer()
            hide()
        }
        binding.recyclerView.show()
    }


    override fun clear() {
        adapterHeader = null
        adapterTop = null
        adapterTopBottom = null
        concatAdapter = null
    }
}
