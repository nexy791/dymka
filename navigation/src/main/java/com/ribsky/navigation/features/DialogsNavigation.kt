package com.ribsky.navigation.features

import com.ribsky.common.navigation.Navigation

interface DialogsNavigation : Navigation {

    fun navigateProgress()

    fun navigateCheckConnection()

    companion object {

        const val RESULT_KEY_PROGRESS = "RESULT_PROGRESS"
        const val RESULT_KEY_CONNECTION = "RESULT_CONNECTION"
    }
}
