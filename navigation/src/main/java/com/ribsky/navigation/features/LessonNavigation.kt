package com.ribsky.navigation.features

import android.content.Intent
import android.os.Parcelable
import androidx.activity.result.ActivityResultLauncher
import com.ribsky.common.navigation.Navigation
import kotlinx.parcelize.Parcelize

interface LessonNavigation : Navigation {

    fun shareMessage(shareWordNavigation: ShareMessageNavigation, text: String)

    fun navigateLesson(lessonId: String, callback: ActivityResultLauncher<Intent>)

    fun navigateMessageAction(message: String)

    @Parcelize
    sealed class ResultAction(open val message: String) : Parcelable {
        class SHARE(override val message: String) : ResultAction(message)
        class COPY(override val message: String) : ResultAction(message)
        class SUPPORT(override val message: String) : ResultAction(message)
    }

    companion object {
        const val KEY_LESSON_ID = "lessonId"
        const val RESULT_KEY_LESSON_ID = "RESULT_LESSON_ID"

        const val KEY_MESSAGE = "messageText"
        const val RESULT_KEY_MESSAGE_ACTION = "RESULT_LESSON_ID"
        const val RESULT_KEY_MESSAGE_ACTION_ID = "RESULT_LESSON_ID"
    }
}
