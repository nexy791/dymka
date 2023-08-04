package com.ribsky.navigation.features

import android.content.Context
import android.content.Intent
import android.os.Parcelable
import androidx.activity.result.ActivityResultLauncher
import com.ribsky.navigation.base.Base
import com.ribsky.navigation.base.NavigationWithResultAndParams
import kotlinx.parcelize.Parcelize

interface LessonNavigation :
    NavigationWithResultAndParams<Context, LessonNavigation.Params, ActivityResultLauncher<Intent>> {

    @Parcelize
    data class Params(val lessonId: String) : Base.Params, Parcelable

    companion object {
        const val KEY_LESSON_ID = "lessonId"
        const val KEY_LESSON_RESULT = "lessonResult"
        const val KEY_STARS_RESULT = "starsResult"
    }
}
