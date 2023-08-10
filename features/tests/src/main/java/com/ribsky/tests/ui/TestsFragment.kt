package com.ribsky.tests.ui

import android.app.Activity
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.ribsky.analytics.Analytics
import com.ribsky.common.alias.commonRaw
import com.ribsky.common.base.BaseFragment
import com.ribsky.common.utils.ext.ActionExt.Companion.openWifiSettings
import com.ribsky.common.utils.ext.ViewExt.Companion.hide
import com.ribsky.common.utils.ext.ViewExt.Companion.show
import com.ribsky.common.utils.ext.ViewExt.Companion.showBottomSheetDialog
import com.ribsky.common.utils.internet.InternetManager
import com.ribsky.common.utils.sound.SoundHelper.playSound
import com.ribsky.core.Resource
import com.ribsky.dialogs.factory.error.ConnectionErrorFactory
import com.ribsky.dialogs.factory.error.ErrorFactory.Companion.showErrorDialog
import com.ribsky.dialogs.factory.progress.ProgressFactory
import com.ribsky.dialogs.factory.streak.StreakPassedFactory
import com.ribsky.dialogs.factory.sub.SubPromptFactory
import com.ribsky.dialogs.factory.success.SuccessFactoryTest
import com.ribsky.navigation.features.BetaNavigation
import com.ribsky.navigation.features.PayWallNavigation
import com.ribsky.navigation.features.ShopNavigation
import com.ribsky.navigation.features.TestNavigation
import com.ribsky.navigation.features.TopDialogsNavigation
import com.ribsky.tests.adapter.test.TestAdapter
import com.ribsky.tests.databinding.FragmentTestsBinding
import com.ribsky.tests.dialogs.info.TestInfoDialog
import com.ribsky.tests.model.TestModel
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class TestsFragment :
    BaseFragment<TestsViewModel, FragmentTestsBinding>(FragmentTestsBinding::inflate) {

    override val viewModel: TestsViewModel by viewModel()
    private val betaNavigation: BetaNavigation by inject()
    private val testNavigation: TestNavigation by inject()
    private val shopNavigation: ShopNavigation by inject()
    private val payWallNavigation: PayWallNavigation by inject()
    private val topDialogsNavigation: TopDialogsNavigation by inject()

    private var adapter: TestAdapter? = null

    private val internetManager: InternetManager by inject()

    private val testListener =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                val isTodayStreak = viewModel.isTodayStreak
                val count = it.data?.getIntExtra(TestNavigation.KEY_TEST_RESULT, 0) ?: 0
                if (count >= 10 && !isTodayStreak) viewModel.updateTodayStreak()
                if (count > 0) showBottomSheetDialog(SuccessFactoryTest(count) {
                    if (count >= 10 && !isTodayStreak) {
                        showBottomSheetDialog(StreakPassedFactory.create() {
                            showWillUp {
                                showPayWall()
                            }
                        })
                    } else {
                        showWillUp {
                            showPayWall()
                        }
                    }
                }.createDialog())
            }
        }

    override fun initView() {
        initAdapter()
        initRecycler()
    }


    private fun initAdapter() {
        adapter = TestAdapter { test ->
            processTestClick(test)
        }
    }

    private fun processTestClick(test: TestModel) {
        playSound(commonRaw.sound_tap)
        if (internetManager.isOnline() || viewModel.isFileExists(test.content)) {
            val dialog = if (test.isInProgress()) {
                ProgressFactory({ betaNavigation.navigate(requireContext()) }).createDialog()
            } else if (!test.isActive) {
                SubPromptFactory {
                    Analytics.logEvent(Analytics.Event.PREMIUM_FROM_WORDS)
                    shopNavigation.navigate(
                        requireContext(),
                        ShopNavigation.Params(Analytics.Event.PREMIUM_BUY_FROM_WORDS)
                    )
                }.createDialog()
            } else {
                TestInfoDialog.newInstance(test.id) {
                    testNavigation.navigate(
                        requireContext(),
                        TestNavigation.Params(test.id),
                        testListener
                    )
                }
            }
            showBottomSheetDialog(dialog)
        } else {
            showBottomSheetDialog(ConnectionErrorFactory({ openWifiSettings() }).createDialog())
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
                Resource.Status.LOADING -> loadContent()
                Resource.Status.SUCCESS -> {
                    adapter?.submitList(result.data) {
                        showContent()
                    }
                }

                Resource.Status.ERROR -> showErrorDialog(result.exception?.localizedMessage) { findNavController().navigateUp() }
            }
        }
    }

    private fun showWillUp(callback: () -> Unit) {
        viewModel.isNeedToShowWillUp {
            if (it.isSuccess) {
                val users = it.getOrNull()!!.map {
                    TopDialogsNavigation.UserModel(
                        name = it.name,
                        score = it.score,
                        avatar = it.image,
                        hasPrem = it.hasPrem
                    )
                }
                topDialogsNavigation.navigate(
                    childFragmentManager,
                    TopDialogsNavigation.Params(
                        TopDialogsNavigation.Status.UP,
                        TopDialogsNavigation.Type.TESTS,
                        users,
                        callback
                    )
                )
            } else {
                showWillMore(callback)
            }
        }
    }

    private fun showWillMore(callback: () -> Unit) {
        viewModel.isNeedToShowMore {
            if (it.isSuccess) {
                val users = it.getOrNull()!!.map {
                    TopDialogsNavigation.UserModel(
                        name = it.name,
                        score = it.score,
                        avatar = it.image,
                        hasPrem = it.hasPrem
                    )
                }
                topDialogsNavigation.navigate(
                    childFragmentManager,
                    TopDialogsNavigation.Params(
                        TopDialogsNavigation.Status.MORE,
                        TopDialogsNavigation.Type.TESTS,
                        users,
                        callback
                    )
                )
            } else {
                callback()
            }
        }
    }

    private fun showPayWall() {
        viewModel.isNeedToShowPayWall {
            if (it.isSuccess) {
                val param = PayWallNavigation.Params(it.getOrNull()!!)
                payWallNavigation.navigate(
                    childFragmentManager,
                    param,
                    object : PayWallNavigation.Callback {
                        override fun onDiscount() {
                            Analytics.logEvent(Analytics.Event.PREMIUM_FROM_PAYWALL)
                            shopNavigation.navigate(
                                requireContext(),
                                ShopNavigation.Params(Analytics.Event.PREMIUM_BUY_FROM_PAYWALL)
                            )
                        }
                    })
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
        adapter = null
    }
}
