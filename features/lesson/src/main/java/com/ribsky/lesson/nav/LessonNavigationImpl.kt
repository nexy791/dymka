package com.ribsky.lesson.nav

import android.content.Context
import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import com.ribsky.lesson.ui.LessonActivity
import com.ribsky.navigation.features.LessonNavigation

class LessonNavigationImpl : LessonNavigation {

    override fun navigate(
        navigation: Context,
        params: LessonNavigation.Params,
        callback: ActivityResultLauncher<Intent>,
    ) {
        callback.launch(
            Intent(navigation, LessonActivity::class.java).apply {
                putExtra(LessonNavigation.KEY_LESSON_ID, params.lessonId)
            }
        )
    }
}
