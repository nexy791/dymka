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
import com.ribsky.article.databinding.FragmentArticleTextBinding
import com.ribsky.article.ui.fragments.base.BaseArticleFragment
import com.ribsky.article.ui.fragments.base.BaseArticleViewModel
import dev.chrisbanes.insetter.applyInsetter
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class ArticleTextFragment :
    BaseArticleFragment<FragmentArticleTextBinding>(FragmentArticleTextBinding::inflate) {

    override val viewModel: BaseArticleViewModel by viewModel()

    override fun initView() {
        binding.root.setBackgroundColor(background)
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
            }
        }
    }

    override fun initObs() {}

    override fun clear() {}

    companion object {

        fun newInstance(
            text: String?,
            bg: String?
        ) = ArticleTextFragment().apply {
            arguments = bundleOf(
                TEXT to text,
                BACKGROUND to bg
            )
        }
    }

}