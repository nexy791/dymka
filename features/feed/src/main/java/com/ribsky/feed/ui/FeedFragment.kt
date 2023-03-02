package com.ribsky.feed.ui

import androidx.fragment.app.setFragmentResultListener
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.GridLayoutManager
import androidx.transition.AutoTransition
import androidx.transition.TransitionManager
import com.redmadrobot.lib.sd.LoadingStateDelegate
import com.ribsky.common.base.BaseFragment
import com.ribsky.common.livedata.Resource.Status
import com.ribsky.domain.model.best.BaseBestWordModel
import com.ribsky.domain.model.paragraph.BaseParagraphModel
import com.ribsky.feed.adapter.lm.FeedSpanSizeLookup
import com.ribsky.feed.adapter.paragraph.ParagraphAdapter
import com.ribsky.feed.adapter.prem.PremAdapter
import com.ribsky.feed.adapter.word.BestWordAdapter
import com.ribsky.feed.databinding.FragmentFeedBinding
import com.ribsky.navigation.features.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class FeedFragment :
    BaseFragment<FeedNavigation, FeedViewModel, FragmentFeedBinding>(FragmentFeedBinding::inflate) {
    override val viewModel: FeedViewModel by viewModel()
    override val navigation: FeedNavigation by inject()
    private val dialogsNavigation: DialogsNavigation by inject()
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
            navigation.navigateShareWord(shareNavigation, id)
        }

        adapterPrem = PremAdapter() {
            navigation.navigateShop(shopNavigation)
        }

        adapterParagraph = ParagraphAdapter { model ->
            if (!model.isEmpty) {
                navigation.navigateLessons(lessonsNavigation, model.id)
            } else {
                navigation.navigateProgress(dialogsNavigation)
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
                Status.ERROR -> {}
            }
        }
        lessonsStatus.observe(viewLifecycleOwner) { result ->
            when (result.status) {
                Status.SUCCESS -> {
                    adapterPrem?.submitList(listOf(viewModel.isSub))
                    updateLessonsContent(result.data!!)
                }
                else -> {}
            }
        }
        setFragmentResultListener(DialogsNavigation.RESULT_KEY_PROGRESS) { _, _ ->
            navigation.navigateBeta(betaNavigation)
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
