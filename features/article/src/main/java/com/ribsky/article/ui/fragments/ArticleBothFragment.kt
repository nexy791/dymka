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
import com.ribsky.article.databinding.FragmentArticleBothBinding
import com.ribsky.article.ui.fragments.base.BaseArticleFragment
import com.ribsky.article.ui.fragments.base.BaseArticleViewModel
import com.ribsky.common.alias.commonDrawable
import com.ribsky.common.utils.glide.ImageLoader.Companion.loadImage
import dev.chrisbanes.insetter.applyInsetter
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class ArticleBothFragment :
    BaseArticleFragment<FragmentArticleBothBinding>(FragmentArticleBothBinding::inflate) {

    override val viewModel: BaseArticleViewModel by viewModel()

    override fun initView() {
        binding.root.setBackgroundColor(background)
        binding.imageView.loadImage(storage.getReferenceFromUrl(image.orEmpty())) {
            placeholder(commonDrawable.placeholder)
            error(commonDrawable.placeholder)
        }
        binding.text.text = text?.parseAsHtml()
        binding.text.movementMethod = ScrollingMovementMethod()
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
                binding.text.isGone = false
                binding.imageView.isGone = false
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
                binding.text.isGone = true
                binding.imageView.isGone = true
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
        ) = ArticleBothFragment().apply {
            arguments = bundleOf(
                TEXT to text,
                IMAGE to image,
                BACKGROUND to bg
            )
        }
    }

}