package com.ribsky.navigation.features

import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import com.ribsky.common.navigation.Navigation

interface LessonsNavigation : Navigation {

    fun navigateSuccess()

    fun navigateProgress(dialogsNavigation: DialogsNavigation)

    fun navigateInternetError(dialogsNavigation: DialogsNavigation)

    fun navigateLessonInfoDialog(lessonId: String)

    fun navigateLesson(
        lessonNavigation: LessonNavigation,
        lessonId: String,
        callback: ActivityResultLauncher<Intent>,
    )

    fun navigateShop(shopNavigation: ShopNavigation)

    fun navigatePromptSub(shopNavigation: ShopNavigation)
    fun navigateBeta(betaNavigation: BetaNavigation)

    companion object {
        const val KEY_ID = "paragraphId"
        const val KEY_LESSON_INFO_ID = "lessonId"

        const val RESULT_KEY_LESSON_INFO = "LESSON_INFO"
        const val RESULT_KEY_LESSON_INFO_ID = "LESSON_INFO_ID"
    }
}
