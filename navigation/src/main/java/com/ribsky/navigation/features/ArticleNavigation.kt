package com.ribsky.navigation.features

import android.content.Context
import android.content.Intent
import android.os.Parcelable
import androidx.activity.result.ActivityResultLauncher
import com.ribsky.navigation.base.Base
import com.ribsky.navigation.base.NavigationWithParams
import com.ribsky.navigation.base.NavigationWithResult
import com.ribsky.navigation.base.NavigationWithResultAndParams
import kotlinx.parcelize.Parcelize

interface ArticleNavigation :
    NavigationWithParams<Context, ArticleNavigation.Params> {

    @Parcelize
    data class Params(val articleId: String) : Base.Params, Parcelable

    companion object {
        const val KEY_ARTICLE_ID = "articleId"
    }
}
