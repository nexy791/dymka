package com.ribsky.feed.ui

import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.GridLayoutManager
import androidx.transition.AutoTransition
import androidx.transition.TransitionManager
import com.redmadrobot.lib.sd.LoadingStateDelegate
import com.ribsky.analytics.Analytics
import com.ribsky.common.base.BaseFragment
import com.ribsky.common.livedata.Resource.Status
import com.ribsky.common.utils.ext.ViewExt.Companion.showBottomSheetDialog
import com.ribsky.dialogs.factory.common.ProgressFactory
import com.ribsky.dialogs.factory.error.ErrorFactory.Companion.showErrorDialog
import com.ribsky.domain.model.best.BaseBestWordModel
import com.ribsky.domain.model.paragraph.BaseParagraphModel
import com.ribsky.feed.adapter.lm.FeedSpanSizeLookup
import com.ribsky.feed.adapter.paragraph.ParagraphAdapter
import com.ribsky.feed.adapter.prem.PremAdapter
import com.ribsky.feed.adapter.word.BestWordAdapter
import com.ribsky.feed.databinding.FragmentFeedBinding
import com.ribsky.navigation.features.BetaNavigation
import com.ribsky.navigation.features.LessonsNavigation
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

    private var adapter: ConcatAdapter? = null
    private var adapterBestWord: BestWordAdapter? = null
    private var adapterParagraph: ParagraphAdapter? = null
    private var adapterPrem: PremAdapter? = null
    private var state: LoadingStateDelegate? = null

    override fun initView() {
        initState()
        initAdapter()
        initRecycler()
        updatePremContent(listOf(viewModel.isSub))
    }

    private fun initAdapter() {
        adapterBestWord = BestWordAdapter { id ->
            shareNavigation.navigate(requireContext(), ShareWordNavigation.Params(id))
        }

        adapterPrem = PremAdapter() {
            Analytics.logEvent(Analytics.Event.PREMIUM_FROM_MAIN)
            shopNavigation.navigate(requireContext())
        }

        adapterParagraph = ParagraphAdapter { model ->
            if (!model.isEmpty) {
                lessonsNavigation.navigate(findNavController(), LessonsNavigation.Params(model.id))
            } else {
                showBottomSheetDialog(ProgressFactory({ betaNavigation.navigate(requireContext()) }).createDialog())
            }
        }

        this@FeedFragment.adapter = ConcatAdapter(adapterBestWord, adapterPrem, adapterParagraph)
    }

    private fun initState() = with(binding) {
        state = LoadingStateDelegate(recyclerView, circularProgressIndicator).apply {
            showLoading()
        }
    }

    private fun initRecycler() = with(binding.recyclerView) {
        layoutManager = GridLayoutManager(requireContext(), 2).apply {
            spanSizeLookup = FeedSpanSizeLookup()
        }
        adapter = this@FeedFragment.adapter
    }

    override fun initObs() = with(viewModel) {
        getBestWord()
        bestWordStatus.observe(viewLifecycleOwner) { result ->
            when (result.status) {
                Status.LOADING -> showLoading()
                Status.SUCCESS -> {
                    updateBestWordContent(listOf(result.data!!))
                    getParagraphs()
                }
                Status.ERROR -> showErrorDialog(result.exception?.localizedMessage) { findNavController().navigateUp() }
            }
        }
        lessonsStatus.observe(viewLifecycleOwner) { result ->
            when (result.status) {
                Status.SUCCESS -> {
                    adapterPrem?.submitList(listOf(viewModel.isSub))
                    updateLessonsContent(result.data!!)
                }
                Status.LOADING -> {}
                Status.ERROR -> showErrorDialog(result.exception?.localizedMessage) { findNavController().navigateUp() }
            }
        }
    }

    private fun showLoading() {
        TransitionManager.beginDelayedTransition(binding.root, AutoTransition())
        state?.showLoading()
    }

    private fun updateBestWordContent(list: List<BaseBestWordModel>) {
        TransitionManager.beginDelayedTransition(binding.root, AutoTransition())
        adapterBestWord?.submitList(list) {}
    }

    private fun updateLessonsContent(list: List<BaseParagraphModel>) {
        TransitionManager.beginDelayedTransition(binding.root, AutoTransition())
        adapterParagraph?.submitList(list) {
            state?.showContent()
        }
    }

    private fun updatePremContent(list: List<Boolean>) {
        TransitionManager.beginDelayedTransition(binding.root, AutoTransition())
        adapterPrem?.submitList(list) {
            state?.showContent()
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.getParagraphs()
    }

    override fun clear() {
        adapter = null
        adapterBestWord = null
        adapterParagraph = null
        adapterPrem = null
        state = null
    }
}
