package com.ribsky.notes.nav

import android.content.Context
import android.content.Intent
import com.ribsky.navigation.features.NotesNavigation
import com.ribsky.notes.ui.NotesActivity

class NotesNavigationImpl : NotesNavigation {

    override fun navigate(navigation: Context, params: NotesNavigation.Params) {
        navigation.startActivity(
            Intent(navigation, NotesActivity::class.java).apply {
                putExtra(NotesNavigation.KEY_PARAGRAPH_ID, params.paragraphId)
            }
        )
    }
}
