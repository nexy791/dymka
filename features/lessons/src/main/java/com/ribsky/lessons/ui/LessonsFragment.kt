package com.ribsky.lessons.ui

import android.app.Activity.RESULT_OK
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.transition.AutoTransition
import androidx.transition.TransitionManager
import com.redmadrobot.lib.sd.LoadingStateDelegate
import com.ribsky.analytics.Analytics
import com.ribsky.common.alias.commonRaw
import com.ribsky.common.base.BaseFragment
import com.ribsky.common.utils.ext.ActionExt.Companion.openWifiSettings
import com.ribsky.common.utils.ext.ViewExt.Companion.showBottomSheetDialog
import com.ribsky.common.utils.internet.InternetManager
import com.ribsky.common.utils.sound.SoundHelper.playSound
import com.ribsky.core.Resource
import com.ribsky.dialogs.factory.error.ConnectionErrorFactory
import com.ribsky.dialogs.factory.error.ErrorFactory.Companion.showErrorDialog
import com.ribsky.dialogs.factory.progress.ProgressFactory
import com.ribsky.dialogs.factory.streak.StreakPassedFactory
import com.ribsky.dialogs.factory.sub.SubPromptFactory
import com.ribsky.dialogs.factory.success.SuccessFactory
import com.ribsky.domain.model.lesson.BaseLessonModel
import com.ribsky.lessons.adapter.lessons.header.LessonsHeaderAdapter
import com.ribsky.lessons.adapter.lessons.item.LessonsAdapter
import com.ribsky.lessons.databinding.FragmentLessonsBinding
import com.ribsky.lessons.dialogs.info.LessonInfoDialog
import com.ribsky.navigation.features.BetaNavigation
import com.ribsky.navigation.features.LessonNavigation
import com.ribsky.navigation.features.LessonsNavigation
import com.ribsky.navigation.features.PayWallNavigation
import com.ribsky.navigation.features.ShopNavigation
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class LessonsFragment :
    BaseFragment<LessonsViewModel, FragmentLessonsBinding>(
        FragmentLessonsBinding::inflate
    ) {
    override val viewModel: LessonsViewModel by viewModel()

    private val shopNavigation: ShopNavigation by inject()
    private val lessonNavigation: LessonNavigation by inject()
    private val betaNavigation: BetaNavigation by inject()
    private val payWallNavigation: PayWallNavigation by inject()

    private var adapter: ConcatAdapter? = null
    private var adapterHeader: LessonsHeaderAdapter? = null
    private var adapterItem: LessonsAdapter? = null
    private var state: LoadingStateDelegate? = null

    private val paragraphId by lazy { requireArguments().getString(LessonsNavigation.KEY_ID)!! }
    private val internetManager: InternetManager by inject()

    private val lessonListener =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == RESULT_OK) {
                val id = it.data?.getStringExtra(LessonNavigation.KEY_LESSON_RESULT)!!
                val isTodayStreak = viewModel.isTodayStreak
                viewModel.updateLesson(id)
                viewModel.getLessons(paragraphId)

                if (!isTodayStreak) {
                    viewModel.updateTodayStreak()
                }

                showBottomSheetDialog(SuccessFactory {
                    if (!isTodayStreak) {
                        showBottomSheetDialog(StreakPassedFactory.create()) {
                            showPayWall()
                        }
                    } else {
                        showPayWall()
                    }
                }.createDialog())
            }
        }

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
        adapterHeader = LessonsHeaderAdapter {
            playSound(commonRaw.sound_tap)
            findNavController().navigateUp()
        }
        adapterItem = LessonsAdapter { model ->
            processLessonClick(model)
        }
        adapter = ConcatAdapter(adapterHeader, adapterItem)
    }

    private fun initRecycler() = with(binding.recyclerView) {
        layoutManager = LinearLayoutManager(activity)
        adapter = this@LessonsFragment.adapter
    }

    override fun initObs() = with(viewModel) {
        viewModel.getLessons(paragraphId)
        lessonsStatus.observe(viewLifecycleOwner) { result ->
            when (result.status) {
                Resource.Status.LOADING -> showLoading()
                Resource.Status.SUCCESS -> adapterItem?.submitList(result.data)
                Resource.Status.ERROR -> showErrorDialog(result.exception?.localizedMessage) { findNavController().navigateUp() }
            }
        }
        paragraphStatus.observe(viewLifecycleOwner) { result ->
            when (result.status) {
                Resource.Status.LOADING -> showLoading()
                Resource.Status.SUCCESS -> {
                    adapterHeader?.submitList(listOf(result.data)) {
                        TransitionManager.beginDelayedTransition(binding.root, AutoTransition())
                        state?.showContent()
                    }
                }

                Resource.Status.ERROR -> showErrorDialog(result.exception?.localizedMessage) { findNavController().navigateUp() }
            }
        }
    }

    private fun showLoading() = with(binding) {
        TransitionManager.beginDelayedTransition(root, AutoTransition())
        state?.showLoading()
    }

    private fun processLessonClick(model: BaseLessonModel) {
        playSound(commonRaw.sound_tap)
        if (internetManager.isOnline() || viewModel.isFileExists(model.content)) {
            val dialog = if (model.isInProgress()) {
                ProgressFactory({ betaNavigation.navigate(requireContext()) }).createDialog()
            } else if (model.hasPrem && !viewModel.isSub) {
                SubPromptFactory {
                    Analytics.logEvent(Analytics.Event.PREMIUM_FROM_LESSON)
                    shopNavigation.navigate(
                        requireContext(),
                        ShopNavigation.Params(Analytics.Event.PREMIUM_BUY_FROM_LESSON)
                    )
                }.createDialog()
            } else {
                LessonInfoDialog.newInstance(model.id) {
                    lessonNavigation.navigate(
                        requireContext(),
                        LessonNavigation.Params(it),
                        lessonListener
                    )
                }
            }
            showBottomSheetDialog(dialog)
        } else {
            showBottomSheetDialog(ConnectionErrorFactory({ openWifiSettings() }).createDialog())
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

    override fun clear() {
        adapter = null
        adapterHeader = null
        adapterItem = null
        state = null
    }
}
