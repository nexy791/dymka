package com.ribsky.article.ui

import androidx.core.text.parseAsHtml
import androidx.viewpager2.widget.ViewPager2
import com.redmadrobot.lib.sd.LoadingStateDelegate
import com.ribsky.analytics.Analytics
import com.ribsky.article.adapter.ArticleViewPager
import com.ribsky.article.databinding.ActivityArticleBinding
import com.ribsky.article.factory.ArticleFactory
import com.ribsky.common.base.BaseActivity
import com.ribsky.common.utils.Const
import com.ribsky.common.utils.ext.ActionExt.Companion.sendEmail
import com.ribsky.common.utils.ext.AlertsExt.Companion.showExitAlert
import com.ribsky.common.utils.ext.ViewExt.Companion.copy
import com.ribsky.common.utils.ext.ViewExt.Companion.showBottomSheetDialog
import com.ribsky.common.utils.share.ShareImage
import com.ribsky.core.Resource
import com.ribsky.dialogs.base.ListDialog
import com.ribsky.dialogs.factory.error.ErrorFactory.Companion.showErrorDialog
import com.ribsky.dialogs.factory.message.MessageActionFactory
import com.ribsky.domain.model.article.slider.BaseSliderModel
import com.ribsky.navigation.features.ArticleNavigation
import dev.chrisbanes.insetter.applyInsetter
import org.koin.androidx.viewmodel.ext.android.viewModel

class ArticleActivity :
    BaseActivity<ArticleViewModel, ActivityArticleBinding>(ActivityArticleBinding::inflate) {

    override val viewModel: ArticleViewModel by viewModel()

    private var adapter: ArticleViewPager? = null

    private var state: LoadingStateDelegate? = null

    private var shareImage: ShareImage? = null

    private val args by lazy { intent.extras!! }
    private val articleId by lazy { args.getString(ArticleNavigation.KEY_ARTICLE_ID)!! }

    override fun initView(): Unit = with(binding) {
        Analytics.logEvent(Analytics.Event.START_ARTICLE)
        initState()
        setupViewPager()
        setupListeners()

        btnBack.applyInsetter { type(statusBars = true) { margin() } }
        indicator.applyInsetter { type(statusBars = true) { margin() } }
        fabNav.applyInsetter { type(navigationBars = true) { margin() } }

    }

    private fun initState() = with(binding) {
        state = LoadingStateDelegate(pager, circularProgressBar).apply {
            showLoading()
        }
    }

    private fun setupViewPager() = with(binding) {
        adapter = ArticleViewPager(this@ArticleActivity)
        pager.adapter = adapter
        binding.indicator.attachTo(binding.pager)
    }

    private fun setupListeners() = with(binding) {
        btnBack.setOnClickListener { finish() }
        fabNav.setOnClickListener {
            showBottomSheetDialog(
                MessageActionFactory(
                    listOf(
                        ListDialog.Item("\uD83D\uDCE3 Поділитися") {
                            share()
                        },
                        ListDialog.Item("✏️ Скопіювати") {
                            val text =
                                viewModel.contentStatus.value?.data?.content?.getOrNull(pager.currentItem)?.text
                            copy(text?.parseAsHtml().toString())
                        },
                        ListDialog.Item("\uD83D\uDC08 Підтримка") {
                            val id = viewModel.articleStatus.value?.data?.id
                            sendEmail(
                                subject = "dymka повідомити про проблему",
                                text = "Добірка #${id}\n\n"
                            )
                        }
                    )
                ).createDialog()
            )
        }
        btnNext.setOnClickListener {
            if (pager.currentItem < adapter!!.itemCount - 1) {
                pager.setCurrentItem(pager.currentItem + 1, true)
            } else {
                finish()
            }
        }
    }

    private fun share() {
        Analytics.logEvent(Analytics.Event.SHARE_ARTICLE)
        shareImage?.clear()
        shareImage = null
        shareImage = ShareImage
            .addContext(this@ArticleActivity)
            .addView(binding.pager)
            .addDescription(Const.TEXT_SHARE)
            .build()
        shareImage?.share()
    }

    override fun initObs() = with(viewModel) {
        getArticle(articleId)
        articleStatus.observe(this@ArticleActivity) { result ->
            when (result.status) {
                Resource.Status.LOADING -> state?.showLoading()
                Resource.Status.SUCCESS -> getContent(result.data!!.content)
                Resource.Status.ERROR -> showErrorDialog(result.exception?.localizedMessage.orEmpty()) { finish() }
            }
        }
        contentStatus.observe(this@ArticleActivity) { result ->
            when (result.status) {
                Resource.Status.LOADING -> state?.showLoading()
                Resource.Status.SUCCESS -> updateStories(result.data!!)
                Resource.Status.ERROR -> showErrorDialog(result.exception?.localizedMessage.orEmpty()) { finish() }
            }
        }
        binding.pager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                if (position == adapter!!.itemCount - 1) {
                    binding.btnNext.text = "Завершити"
                } else {
                    binding.btnNext.text = "Далі"
                }
            }
        })
    }

    override fun onBackPressed() {
        showExitAlert(
            positiveAction = { finish() },
            negativeAction = { }
        )
    }

    private fun updateStories(data: BaseSliderModel) {
        val content = data.content
        val fragments = content.mapNotNull { item -> ArticleFactory.createArticleFragment(item) }
        adapter?.setFragments(fragments)
        state?.showContent()
    }

    override fun clear() {
        adapter = null
        state = null
        shareImage?.clear()
        shareImage = null
    }


}