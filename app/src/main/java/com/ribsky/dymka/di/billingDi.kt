package com.ribsky.dymka.di

import com.ribsky.billing.client.BillingClient
import com.ribsky.billing.client.BillingClientImpl
import com.ribsky.billing.manager.SubManager
import com.ribsky.billing.manager.SubMangerImpl
import com.ribsky.billing.wrapper.BillingClientWrapper
import com.ribsky.billing.wrapper.BillingClientWrapperImpl
import org.koin.dsl.module

val billingDi = module {

    factory<BillingClient> {
        BillingClientImpl()
    }

    factory<SubManager> {
        SubMangerImpl(
            sharedPreferences = get()
        )
    }

    factory<BillingClientWrapper> {
        BillingClientWrapperImpl(
            billingClient = get(),
            subManager = get()
        )
    }
}
