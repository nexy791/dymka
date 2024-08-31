package com.ribsky.articles.dialogs.info

import androidx.core.os.bundleOf
import com.google.firebase.storage.FirebaseStorage
import com.ribsky.articles.databinding.DialogArticleInfoBinding
import com.ribsky.common.R
import com.ribsky.common.alias.commonDrawable
import com.ribsky.common.alias.commonRaw
import com.ribsky.common.base.BaseSheet
import com.ribsky.common.utils.glide.ImageLoader.Companion.loadImage
import com.ribsky.common.utils.sound.SoundHelper.playSound
import com.ribsky.core.Resource
import com.ribsky.dialogs.factory.error.ErrorFactory.Companion.showErrorDialog
import com.ribsky.domain.model.article.BaseArticleModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.java.KoinJavaComponent.inject

class ArticleInfoDialog(
    private val callback: ((String) -> Unit)? = null,
) : BaseSheet<DialogArticleInfoBinding>(DialogArticleInfoBinding::inflate) {

    private val articleId by lazy { requireArguments().getString(KEY_ARTICLE_ID) }
    private val viewModel: ArticleInfoViewModel by viewModel()

    private val storage: FirebaseStorage by inject(FirebaseStorage::class.java)

    override fun initViews() {
        if (callback == null || articleId == null) dismiss()
        initBtns()
    }

    private fun initBtns() = with(binding) {
        btnCancel.setOnClickListener {
            dismiss()
        }
        btnNext.setOnClickListener {
            playSound(commonRaw.sound_celebration)
            callback?.invoke(articleId!!)
            dismiss()
        }
    }

    private fun updateUi(article: BaseArticleModel) = with(binding) {
        tvTitle.text = article.name
        tvDescription.text = article.description
        imageView.loadImage(storage.getReferenceFromUrl(article.image)) {
            error(R.drawable.placeholder)
            placeholder(commonDrawable.placeholder_content)
        }
        btnNext.isEnabled = true
    }

    override fun initObserves() = with(viewModel) {
        articleId?.let { getLesson(it) }
        articleStatus.observe(viewLifecycleOwner) { result ->
            when (result.status) {
                Resource.Status.SUCCESS -> updateUi(result.data!!)
                Resource.Status.LOADING -> {}
                Resource.Status.ERROR -> showErrorDialog(result.exception?.localizedMessage) { dismiss() }
            }
        }
    }

    override fun clear() {}

    companion object {
        private const val KEY_ARTICLE_ID = "KEY_ARTICLE_ID"
        fun newInstance(articleId: String, callback: (String) -> Unit) =
            ArticleInfoDialog(callback).apply {
                arguments = bundleOf(KEY_ARTICLE_ID to articleId)
            }
    }
}