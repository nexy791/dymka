package com.ribsky.article.nav

import android.content.Context
import android.content.Intent
import com.ribsky.article.ui.ArticleActivity
import com.ribsky.navigation.features.ArticleNavigation

class ArticleNavigationImpl : ArticleNavigation {

    override fun navigate(navigation: Context, params: ArticleNavigation.Params) {
        navigation.startActivity(
            Intent(navigation, ArticleActivity::class.java).apply {
                putExtra(ArticleNavigation.KEY_ARTICLE_ID, params.articleId)
            }
        )
    }
}
