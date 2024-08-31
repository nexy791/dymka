package com.ribsky.dialogs.factory.sub

import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.ribsky.billing.BillingState

class SubPrompt {

    companion object {

        fun navigateSub(billingState: BillingState?, callback: () -> Unit, ): BottomSheetDialogFragment {
            return when (billingState) {
                is BillingState.WelcomeDiscount -> SubPromptWelcomeFactory(billingState, callback)
                else -> SubPromptFactory(billingState, callback)
            }
        }
    }

}