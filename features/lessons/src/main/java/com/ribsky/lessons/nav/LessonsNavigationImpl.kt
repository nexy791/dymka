package com.ribsky.lessons.nav

import androidx.core.os.bundleOf
import androidx.navigation.NavController
import com.ribsky.navigation.alias.navId
import com.ribsky.navigation.features.LessonsNavigation

class LessonsNavigationImpl : LessonsNavigation {
    override fun navigate(navigation: NavController, params: LessonsNavigation.Params) {
        navigation.navigate(
            navId.lessonsFragment,
            bundleOf(LessonsNavigation.KEY_ID to params.paragraphId)
        )
    }
}
