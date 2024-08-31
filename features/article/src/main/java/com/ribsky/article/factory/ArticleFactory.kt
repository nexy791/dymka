package com.ribsky.article.factory

import androidx.fragment.app.Fragment
import com.ribsky.article.ui.fragments.ArticleBothFragment
import com.ribsky.article.ui.fragments.ArticleImageFragment
import com.ribsky.article.ui.fragments.ArticleTextFragment
import com.ribsky.article.ui.fragments.base.BaseArticleFragment
import com.ribsky.domain.model.article.slider.BaseSliderModel

class ArticleFactory {

    companion object {

        fun createArticleFragment(item: BaseSliderModel.BaseSliderType?): BaseArticleFragment<*>? {
            return when (item) {
                is BaseSliderModel.BaseSliderType.Both -> ArticleBothFragment.newInstance(text = item.text, image = item.image, bg = item.background)
                is BaseSliderModel.BaseSliderType.Image -> ArticleImageFragment.newInstance(text = item.text, image = item.image, bg = item.background)
                is BaseSliderModel.BaseSliderType.Text -> ArticleTextFragment.newInstance(item.text, bg = item.background)
                else -> null
            }
        }

    }

}