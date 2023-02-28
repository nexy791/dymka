package com.ribsky.lessons.nav

import android.content.Intent
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import com.ribsky.navigation.alias.navId
import com.ribsky.navigation.features.*

class LessonsNavigationImpl : LessonsNavigation {

    override var activity: AppCompatActivity? = null
    override var navController: NavController? = null

    override fun navigateSuccess() {
        navController?.navigate(navId.lessonSuccessDialog)
    }

    override fun navigateProgress(dialogsNavigation: DialogsNavigation) {
        dialogsNavigation.setup(activity, navController)
        dialogsNavigation.navigateProgress()
    }

    override fun navigateInternetError(dialogsNavigation: DialogsNavigation) {
        dialogsNavigation.setup(activity, navController)
        dialogsNavigation.navigateCheckConnection()
    }

    override fun navigateLessonInfoDialog(lessonId: String) {
        navController?.navigate(
            navId.lessonInfoDialog,
            bundleOf(LessonsNavigation.KEY_LESSON_INFO_ID to lessonId)
        )
    }

    override fun navigateLesson(
        lessonNavigation: LessonNavigation,
        lessonId: String,
        callback: ActivityResultLauncher<Intent>,
    ) {
        lessonNavigation.setup(activity, navController)
        lessonNavigation.navigateLesson(lessonId, callback)
    }

    override fun navigateShop(shopNavigation: ShopNavigation) {
        shopNavigation.setup(activity, navController)
        shopNavigation.navigateHome(null)
    }

    override fun navigatePromptSub(shopNavigation: ShopNavigation) {
        shopNavigation.setup(activity, navController)
        shopNavigation.navigatePromptSub()
    }

    override fun navigateBeta(betaNavigation: BetaNavigation) {
        betaNavigation.setup(activity, navController)
        betaNavigation.navigateHome(null)
    }

    override fun navigateHome(bundle: Bundle?) {
        navController?.navigate(navId.lessonsFragment, bundle)
    }
}
