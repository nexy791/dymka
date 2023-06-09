package com.ribsky.dialogs.base

import com.google.android.material.bottomsheet.BottomSheetDialogFragment

interface DialogFactory {
    fun createDialog(): BottomSheetDialogFragment
}
