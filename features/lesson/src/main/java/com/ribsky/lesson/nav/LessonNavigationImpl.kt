package com.ribsky.lesson.nav

import android.content.Intent
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import com.ribsky.lesson.dialogs.action.MessageActionDialog
import com.ribsky.lesson.ui.LessonActivity
import com.ribsky.navigation.features.LessonNavigation
import com.ribsky.navigation.features.ShareMessageNavigation

class LessonNavigationImpl : LessonNavigation {

    override var activity: AppCompatActivity? = null
    override var navController: NavController? = null

    override fun shareMessage(shareWordNavigation: ShareMessageNavigation, text: String) {
        shareWordNavigation.setup(activity, navController)
        shareWordNavigation.navigateHome(bundleOf(ShareMessageNavigation.KEY_SHARE_MESSAGE_ID to text))
    }

    override fun navigateLesson(lessonId: String, callback: ActivityResultLauncher<Intent>) {
        callback.launch(
            Intent(activity, LessonActivity::class.java).apply {
                putExtra(LessonNavigation.KEY_LESSON_ID, lessonId)
            }
        )
    }

    override fun navigateMessageAction(message: String) {
        MessageActionDialog.newInstance(message)
            .show(activity!!.supportFragmentManager, MessageActionDialog.TAG)
    }

    override fun navigateHome(bundle: Bundle?) {
    }
}
