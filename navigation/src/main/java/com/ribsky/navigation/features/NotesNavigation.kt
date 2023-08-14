package com.ribsky.navigation.features

import android.content.Context
import android.os.Parcelable
import com.ribsky.navigation.base.Base
import com.ribsky.navigation.base.NavigationWithParams
import kotlinx.parcelize.Parcelize

interface NotesNavigation :
    NavigationWithParams<Context, NotesNavigation.Params> {

    @Parcelize
    data class Params(val paragraphId: String) : Base.Params, Parcelable

    companion object {
        const val KEY_PARAGRAPH_ID = "key_paragraph_id"
    }

}