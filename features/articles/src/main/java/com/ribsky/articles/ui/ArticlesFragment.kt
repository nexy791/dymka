package com.ribsky.articles.ui

import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ribsky.analytics.Analytics
import com.ribsky.articles.adapter.articles.ArticlesAdapter
import com.ribsky.articles.adapter.categories.CategoriesAdapter
import com.ribsky.articles.databinding.FragmentArticlesBinding
import com.ribsky.articles.dialogs.info.ArticleInfoDialog
import com.ribsky.common.alias.commonRaw
import com.ribsky.common.base.BaseFragment
import com.ribsky.common.utils.ext.ActionExt.Companion.openWifiSettings
import com.ribsky.common.utils.ext.ViewExt.Companion.hide
import com.ribsky.common.utils.ext.ViewExt.Companion.show
import com.ribsky.common.utils.ext.ViewExt.Companion.showBottomSheetDialog
import com.ribsky.common.utils.internet.InternetManager
import com.ribsky.common.utils.sound.SoundHelper
import com.ribsky.core.Resource
import com.ribsky.dialogs.factory.error.ConnectionErrorFactory
import com.ribsky.dialogs.factory.error.ErrorFactory.Companion.showErrorDialog
import com.ribsky.dialogs.factory.progress.ProgressFactory
import com.ribsky.dialogs.factory.sub.SubPrompt.Companion.navigateSub
import com.ribsky.domain.model.article.BaseArticleModel
import com.ribsky.navigation.features.ArticleNavigation
import com.ribsky.navigation.features.BetaNavigation
import com.ribsky.navigation.features.ShopNavigation
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class ArticlesFragment :
    BaseFragment<ArticlesViewModel, FragmentArticlesBinding>(FragmentArticlesBinding::inflate) {

    override val viewModel: ArticlesViewModel by viewModel()

    private val shopNavigation: ShopNavigation by inject()
    private val betaNavigation: BetaNavigation by inject()
    private val articleNavigation: ArticleNavigation by inject()

    private var adapter: ArticlesAdapter? = null
    private var adapterCategories: CategoriesAdapter? = null

    private val internetManager: InternetManager by inject()


    override fun initView() {
        initAdapter()
        initRecyclerView()
    }

    private fun initAdapter() {
        adapter = ArticlesAdapter { article ->
            processArticleClick(article)
        }
        adapterCategories = CategoriesAdapter { category ->
            viewModel.getArticlesByCategory(category)
        }
    }

    private fun initRecyclerView() {
        with(binding.recyclerView) {
            layoutManager =
                LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
            adapter = this@ArticlesFragment.adapter
        }
        with(binding.rvCategories) {
            layoutManager =
                LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
            adapter = this@ArticlesFragment.adapterCategories
        }
    }

    private fun processArticleClick(model: BaseArticleModel) {
        SoundHelper.playSound(commonRaw.sound_tap)
        if (internetManager.isOnline() || viewModel.isFileExists(model.content)) {
            val dialog = if (model.isInProgress()) {
                ProgressFactory({ betaNavigation.navigate(requireContext()) }).createDialog()
            } else if (model.hasPrem && !viewModel.isSub) {
                navigateSub(viewModel.discount) {
                    Analytics.logEvent(Analytics.Event.PREMIUM_FROM_ARTICLE)
                    shopNavigation.navigate(
                        requireContext(),
                        ShopNavigation.Params(Analytics.Event.PREMIUM_BUY_FROM_ARTICLE)
                    )
                }
            } else {
                ArticleInfoDialog.newInstance(model.id) { articleId ->
                    articleNavigation.navigate(
                        requireContext(),
                        ArticleNavigation.Params(articleId)
                    )
                }
            }
            showBottomSheetDialog(dialog)
        } else {
            showBottomSheetDialog(ConnectionErrorFactory({ openWifiSettings() }).createDialog())
        }
    }

    override fun initObs() = with(viewModel) {
        viewModel.getArticles()
        articles.observe(viewLifecycleOwner) { result ->
            when (result.status) {
                Resource.Status.LOADING -> loadContent()
                Resource.Status.SUCCESS -> {
                    adapter?.submitList(result.data) {
                        showContent()
                        binding.recyclerView.scrollToPosition(0)
                    }
                }

                Resource.Status.ERROR -> showErrorDialog(result.exception?.localizedMessage) { findNavController().navigateUp() }
            }
        }
        categories.observe(viewLifecycleOwner) { result ->
            when (result.status) {
                Resource.Status.LOADING -> {}
                Resource.Status.SUCCESS -> {
                    adapterCategories?.submitList(result.data)
                }

                Resource.Status.ERROR -> showErrorDialog(result.exception?.localizedMessage) { findNavController().navigateUp() }
            }

        }

    }

    private fun loadContent() = with(binding) {
        recyclerView.hide()
        rvCategories.hide()
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
        rvCategories.show()
    }

    override fun clear() {
        adapter = null
        adapterCategories = null
    }

}