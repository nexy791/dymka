package com.ribsky.lessons.ui

import android.app.Activity.RESULT_OK
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
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
import com.ribsky.dialogs.factory.stars.StarsFactory
import com.ribsky.dialogs.factory.stars.SuccessStarsFactory
import com.ribsky.dialogs.factory.streak.StreakPassedFactory
import com.ribsky.dialogs.factory.sub.SubPromptFactory
import com.ribsky.dialogs.factory.success.SuccessFactory
import com.ribsky.domain.model.lesson.BaseLessonModel
import com.ribsky.lessons.adapter.lessons.header.LessonsHeaderAdapter
import com.ribsky.lessons.adapter.lessons.item.LessonsAdapter
import com.ribsky.lessons.adapter.stars.StarAdapter
import com.ribsky.lessons.databinding.FragmentLessonsBinding
import com.ribsky.lessons.dialogs.info.LessonInfoDialog
import com.ribsky.lessons.model.StarModel
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
    private var adapterStar: StarAdapter? = null
    private var adapterItem: LessonsAdapter? = null

    private val paragraphId by lazy { requireArguments().getString(LessonsNavigation.KEY_ID)!! }
    private val internetManager: InternetManager by inject()

    private val lessonListener =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == RESULT_OK) {
                val id = it.data?.getStringExtra(LessonNavigation.KEY_LESSON_RESULT)!!
                val stars = it.data?.getFloatExtra(LessonNavigation.KEY_STARS_RESULT, 0f)!!
                val isTodayStreak = viewModel.isTodayStreak
                val lesson = viewModel.lessons?.firstOrNull { model -> model.id == id }
                viewModel.updateLesson(paragraphId, id, stars)

                if (!isTodayStreak) {
                    viewModel.updateTodayStreak()
                }
                showBottomSheetDialog(SuccessFactory {
                    if (!isTodayStreak) {
                        showStarsDialog(stars, (lesson?.stars ?: 0f).toFloat()) {
                            showBottomSheetDialog(StreakPassedFactory.create {
                                showPayWall()
                            })
                        }
                    } else {
                        showStarsDialog(stars, (lesson?.stars ?: 0f).toFloat()) {
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
        adapterHeader = LessonsHeaderAdapter {
            playSound(commonRaw.sound_tap)
            findNavController().navigateUp()
        }
        adapterItem = LessonsAdapter { model ->
            processLessonClick(model)
        }
        adapterStar = StarAdapter {
            processStarClick(it)
        }
        adapter = ConcatAdapter(adapterHeader, adapterStar, adapterItem)
    }

    private fun processStarClick(star: StarModel) {
        showBottomSheetDialog(StarsFactory(star.stars, star.allStars, negativeButtonCallback = {
            showBottomSheetDialog(SubPromptFactory {
                Analytics.logEvent(Analytics.Event.PREMIUM_FROM_STARS)
                shopNavigation.navigate(
                    requireActivity(),
                    ShopNavigation.Params(Analytics.Event.PREMIUM_BUY_FROM_STARS)
                )
            }.createDialog())
        }
        ).createDialog())
    }

    private fun initRecycler() = with(binding.recyclerView) {
        layoutManager = LinearLayoutManager(activity)
        adapter = this@LessonsFragment.adapter
    }

    override fun initObs() = with(viewModel) {
        viewModel.getLessons(paragraphId)
        lessonsStatus.observe(viewLifecycleOwner) { result ->
            when (result.status) {
                Resource.Status.LOADING -> loadContent()
                Resource.Status.SUCCESS -> {
                    adapterItem?.submitList(result.data)
                    updateStarView(result.data!!)
                }

                Resource.Status.ERROR -> showErrorDialog(result.exception?.localizedMessage) { findNavController().navigateUp() }
            }
        }
        paragraphStatus.observe(viewLifecycleOwner) { result ->
            when (result.status) {
                Resource.Status.LOADING -> loadContent()
                Resource.Status.SUCCESS -> {
                    adapterHeader?.submitList(listOf(result.data)) {
                        showContent()
                    }
                }

                Resource.Status.ERROR -> showErrorDialog(result.exception?.localizedMessage) { findNavController().navigateUp() }
            }
        }
    }

    private fun updateStarView(data: List<BaseLessonModel>) {
        adapterStar?.submitList(
            listOf(
                StarModel(
                    stars = data.sumOf { it.stars },
                    allStars = data.size * 3
                )
            )
        )
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

    private fun showStarsDialog(stars: Float, starsLesson: Float, callback: () -> Unit) {
        if (starsLesson < stars) {
            showBottomSheetDialog(SuccessStarsFactory.create(stars) {
                callback()
            })
        } else {
            callback()
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
        adapterHeader = null
        adapterItem = null
    }
}
