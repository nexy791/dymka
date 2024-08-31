package com.ribsky.article.ui.fragments

import android.text.method.ScrollingMovementMethod
import androidx.core.os.bundleOf
import androidx.core.text.parseAsHtml
import androidx.core.view.isGone
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.transition.TransitionManager
import com.google.android.material.transition.MaterialSharedAxis
import com.ribsky.article.databinding.FragmentArticleImageBinding
import com.ribsky.article.ui.fragments.base.BaseArticleFragment
import com.ribsky.article.ui.fragments.base.BaseArticleViewModel
import com.ribsky.common.alias.commonDrawable
import com.ribsky.common.utils.glide.ImageLoader.Companion.loadImage
import dev.chrisbanes.insetter.applyInsetter
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class ArticleImageFragment :
    BaseArticleFragment<FragmentArticleImageBinding>(FragmentArticleImageBinding::inflate) {

    override val viewModel: BaseArticleViewModel by viewModel()

    override fun initView() {
        binding.root.setBackgroundColor(background)
        binding.image.loadImage(storage.getReference(image.orEmpty())) {
            placeholder(commonDrawable.placeholder)
            error(commonDrawable.placeholder)
        }
        binding.title.text = text?.parseAsHtml()
        binding.title.movementMethod = ScrollingMovementMethod()
        binding.guideline.applyInsetter { type(statusBars = true) { padding() } }
        binding.guideline2.applyInsetter { type(navigationBars = true) { padding() } }
    }

    override fun loadWithAnim() {
        if (isDetached) return
        if (!isAdded) return
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                TransitionManager.beginDelayedTransition(
                    binding.root,
                    MaterialSharedAxis(MaterialSharedAxis.Y, true)
                )
                binding.image.isGone = false
                binding.title.isGone = false
            }
        }
    }

    override fun clearWithAnim() {
        if (isDetached) return
        if (!isAdded) return
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                TransitionManager.beginDelayedTransition(
                    binding.root,
                    MaterialSharedAxis(MaterialSharedAxis.Y, false)
                )
                binding.image.isGone = true
                binding.title.isGone = true
            }
        }
    }

    override fun initObs() {}

    override fun clear() {}

    companion object {

        fun newInstance(
            text: String?,
            image: String,
            bg: String?
        ) = ArticleImageFragment().apply {
            arguments = bundleOf(
                TEXT to text,
                IMAGE to image,
                BACKGROUND to bg
            )
        }
    }

}