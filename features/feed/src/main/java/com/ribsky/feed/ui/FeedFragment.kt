package com.ribsky.feed.ui

import android.content.Intent
import android.net.Uri
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.GridLayoutManager
import com.ribsky.analytics.Analytics
import com.ribsky.common.alias.commonRaw
import com.ribsky.common.base.BaseFragment
import com.ribsky.common.utils.ext.AlertsExt.Companion.showAlert
import com.ribsky.common.utils.ext.ViewExt.Companion.hide
import com.ribsky.common.utils.ext.ViewExt.Companion.show
import com.ribsky.common.utils.ext.ViewExt.Companion.showBottomSheetDialog
import com.ribsky.common.utils.sound.SoundHelper.playSound
import com.ribsky.core.Resource.Status
import com.ribsky.dialogs.factory.error.ErrorFactory.Companion.showErrorDialog
import com.ribsky.dialogs.factory.limit.LimitFactory
import com.ribsky.dialogs.factory.progress.ProgressFactory
import com.ribsky.dialogs.factory.sub.SubPrompt.Companion.navigateSub
import com.ribsky.domain.model.best.BaseBestWordModel
import com.ribsky.domain.model.paragraph.BaseParagraphModel
import com.ribsky.domain.model.promo.BasePromoModel
import com.ribsky.feed.adapter.lm.FeedSpanSizeLookup
import com.ribsky.feed.adapter.paragraph.ParagraphAdapter
import com.ribsky.feed.adapter.promo.PromoAdapter
import com.ribsky.feed.adapter.streak.StreakAdapter
import com.ribsky.feed.adapter.word.BestWordAdapter
import com.ribsky.feed.databinding.FragmentFeedBinding
import com.ribsky.navigation.features.BetaNavigation
import com.ribsky.navigation.features.LessonsNavigation
import com.ribsky.navigation.features.ShareStreakNavigation
import com.ribsky.navigation.features.ShareWordNavigation
import com.ribsky.navigation.features.ShopNavigation
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class FeedFragment :
    BaseFragment<FeedViewModel, FragmentFeedBinding>(FragmentFeedBinding::inflate) {
    override val viewModel: FeedViewModel by viewModel()

    private val shopNavigation: ShopNavigation by inject()
    private val lessonsNavigation: LessonsNavigation by inject()
    private val betaNavigation: BetaNavigation by inject()
    private val shareNavigation: ShareWordNavigation by inject()
    private val shareStreakNavigation: ShareStreakNavigation by inject()

    private var adapter: ConcatAdapter? = null
    private var adapterBestWord: BestWordAdapter? = null
    private var adapterPromo: PromoAdapter? = null
    private var adapterParagraph: ParagraphAdapter? = null
    private var adapterStreak: StreakAdapter? = null

    override fun initView() {
        initAdapter()
        initRecycler()
    }

    private fun initAdapter() {
        adapterBestWord = BestWordAdapter { id ->
            shareNavigation.navigate(requireContext(), ShareWordNavigation.Params(id))
        }

        adapterPromo = PromoAdapter {
            Analytics.logEvent(Analytics.Event.PROMO_CLICK)
            if (it.contains("https")) {
                requireActivity().showAlert(
                    title = "Оголошення",
                    message = "Відкрити сайт: " + it + " ?",
                    positiveButton = "Добре",
                    positiveAction = {
                        Analytics.logEvent(Analytics.Event.PROMO_OPEN)
                        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(it)))
                    },
                    negativeButton = "Закрити",
                    negativeAction = { }
                )
            } else if (it.isNotBlank()) {
                requireActivity().showAlert(
                    title = "Оголошення",
                    message = it,
                    positiveButton = "Добре",
                    positiveAction = {
                        Analytics.logEvent(Analytics.Event.PROMO_OPEN)
                    },
                    negativeButton = "Закрити",
                    negativeAction = { }
                )
            }
        }

        adapterParagraph = ParagraphAdapter { model ->
            processParagraphClick(model)
        }

        adapterStreak = StreakAdapter {
            shareStreakNavigation.navigate(
                requireContext(), ShareStreakNavigation.Params(
                    count = viewModel.currentStreak,
                    isDone = viewModel.isTodayStreak
                )
            )
        }

        this@FeedFragment.adapter =
            ConcatAdapter(adapterBestWord, adapterPromo, adapterStreak, adapterParagraph)
    }

    private fun processParagraphClick(model: BaseParagraphModel) {
        playSound(commonRaw.sound_tap)
        if (model.isEmpty) {
            showBottomSheetDialog(ProgressFactory({ betaNavigation.navigate(requireContext()) }).createDialog())
        } else if (model.isEnoughStars || viewModel.isSub) {
            lessonsNavigation.navigate(findNavController(), LessonsNavigation.Params(model.id))
        } else {
            showBottomSheetDialog(
                LimitFactory(
                    onConfirm = {},
                    onDismiss = {
                        showBottomSheetDialog(navigateSub(viewModel.discount) {
                            Analytics.logEvent(Analytics.Event.PREMIUM_FROM_STARS)
                            shopNavigation.navigate(
                                requireActivity(),
                                ShopNavigation.Params(Analytics.Event.PREMIUM_BUY_FROM_STARS)
                            )
                        })
                    },
                    stars = model.starsHave,
                    needStars = model.stars
                ).createDialog()
            )
        }
    }

    private fun initRecycler() = with(binding.recyclerView) {
        layoutManager = GridLayoutManager(requireContext(), 2)
        adapter = this@FeedFragment.adapter
    }

    override fun initObs() = with(viewModel) {
        getBestWord()
        // TODO:
        adapterStreak?.submitList(
            listOf(
                StreakAdapter.StreakModel(
                    streak = currentStreak,
                    isToday = isTodayStreak
                )
            )
        )
        bestWordStatus.observe(viewLifecycleOwner) { result ->
            when (result.status) {
                Status.LOADING -> loadContent()
                Status.SUCCESS -> {
                    updateBestWordContent(listOf(result.data!!))
                    getParagraphs()
                }

                Status.ERROR -> showErrorDialog(result.exception?.localizedMessage) { findNavController().navigateUp() }
            }
        }
        paragraphsStatus.observe(viewLifecycleOwner) { result ->
            when (result.status) {
                Status.SUCCESS -> {
                    updateLessonsContent(result.data!!)
                    getPromo()
                }

                Status.LOADING -> {}
                Status.ERROR -> showErrorDialog(result.exception?.localizedMessage) { findNavController().navigateUp() }
            }
        }
        promoStatus.observe(viewLifecycleOwner) { result ->
            when (result.status) {
                Status.SUCCESS -> {
                    binding.recyclerView.layoutManager = binding.recyclerView.layoutManager?.apply {
                        (this as GridLayoutManager).spanSizeLookup = FeedSpanSizeLookup(true)
                    }
                    updatePromoContent(listOf(result.data!!))
                    showContent()
                }

                Status.LOADING -> {}
                Status.ERROR -> {
                    binding.recyclerView.layoutManager = binding.recyclerView.layoutManager?.apply {
                        (this as GridLayoutManager).spanSizeLookup = FeedSpanSizeLookup(false)
                    }
                    showContent()
                    Analytics.logEvent(Analytics.Event.PROMO_FEED_ERROR)
                }
            }
        }
    }


    private fun updateBestWordContent(list: List<BaseBestWordModel>) {
        adapterBestWord?.submitList(list) {}
    }

    private fun updateLessonsContent(list: List<BaseParagraphModel>) {
        adapterParagraph?.submitList(list) {}
    }

    private fun updatePromoContent(list: List<BasePromoModel>) {
        adapterPromo?.submitList(list)
    }

    override fun onResume() {
        super.onResume()
        viewModel.getBestWord()
    }

    private fun loadContent() = with(binding) {
        recyclerView.hide()
        placeholder.root.apply {
            startShimmer()
            show()
        }
    }

    private fun showContent() = with(binding) {
        placeholder.root.apply {
            stopShimmer()
            hide()
        }
        recyclerView.show()
    }


    override fun clear() {
        adapter = null
        adapterBestWord = null
        adapterParagraph = null
        adapterPromo = null
        adapterStreak = null
    }
}
