package com.ribsky.tests.ui

import androidx.fragment.app.setFragmentResultListener
import androidx.recyclerview.widget.GridLayoutManager
import androidx.transition.AutoTransition
import androidx.transition.TransitionManager
import com.redmadrobot.lib.sd.LoadingStateDelegate
import com.ribsky.common.base.BaseFragment
import com.ribsky.common.livedata.Resource
import com.ribsky.common.utils.ext.ActionExt.Companion.openWifiSettings
import com.ribsky.common.utils.internet.InternetManager
import com.ribsky.navigation.features.*
import com.ribsky.tests.adapter.test.TestAdapter
import com.ribsky.tests.databinding.FragmentTestsBinding
import com.ribsky.tests.model.TestModel
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class TestsFragment :
    BaseFragment<TestsNavigation, TestsViewModel, FragmentTestsBinding>(FragmentTestsBinding::inflate) {

    override val viewModel: TestsViewModel by viewModel()
    override val navigation: TestsNavigation by inject()
    private val dialogsNavigation: DialogsNavigation by inject()
    private val betaNavigation: BetaNavigation by inject()
    private val testNavigation: TestNavigation by inject()
    private val shopNavigation: ShopNavigation by inject()

    private var state: LoadingStateDelegate? = null
    private var adapter: TestAdapter? = null

    private val internetManager: InternetManager by inject()

    override fun initView() {
        initState()
        initAdapter()
        initRecycler()
    }

    private fun initState() = with(binding) {
        state = LoadingStateDelegate(recyclerView, circularProgressIndicator).apply {
            TransitionManager.beginDelayedTransition(root, AutoTransition())
            showLoading()
        }
    }

    private fun initAdapter() {
        adapter = TestAdapter { test ->
            processTestClick(test)
        }
    }

    private fun processTestClick(test: TestModel) {
        if (internetManager.isOnline() || viewModel.isFileExists(test.content)) {
            if (!test.isInProgress()) {
                if (test.isActive) {
                    navigation.navigateTestInfoDialog(test.id)
                } else {
                    navigation.navigateSubPrompt(shopNavigation)
                }
            } else {
                navigation.navigateProgress(dialogsNavigation)
            }
        } else {
            navigation.navigateInternetError(dialogsNavigation)
        }
    }

    private fun initRecycler() = with(binding) {
        recyclerView.apply {
            layoutManager = GridLayoutManager(activity, 2)
            adapter = this@TestsFragment.adapter
        }
    }

    override fun initObs() = with(viewModel) {
        testStatus.observe(viewLifecycleOwner) { result ->
            when (result.status) {
                Resource.Status.LOADING -> {
                    TransitionManager.beginDelayedTransition(binding.root, AutoTransition())
                    state?.showLoading()
                }
                Resource.Status.SUCCESS -> {
                    adapter?.submitList(result.data) {
                        TransitionManager.beginDelayedTransition(binding.root, AutoTransition())
                        state?.showContent()
                    }
                }
                Resource.Status.ERROR -> {}
            }
        }
        setFragmentResultListener(TestsNavigation.RESULT_KEY_TEST_INFO) { _, bundle ->
            val testId = bundle.getString(TestsNavigation.RESULT_KEY_TEST_INFO_ID)!!
            navigation.navigateDetails(testNavigation, testId)
        }
        setFragmentResultListener(ShopNavigation.RESULT_KEY_PROMPT_SUB) { _, _ ->
            navigation.navigateShop(shopNavigation)
        }
        setFragmentResultListener(DialogsNavigation.RESULT_KEY_PROGRESS) { _, _ ->
            navigation.navigateBeta(betaNavigation)
        }
        setFragmentResultListener(DialogsNavigation.RESULT_KEY_CONNECTION) { _, _ ->
            openWifiSettings()
        }
    }

    override fun clear() {
        state = null
        adapter = null
    }
}
