package com.ribsky.dymka.di

import com.ribsky.common.utils.dynamic.DynamicModule
import com.ribsky.common.utils.dynamic.DynamicModuleImpl
import com.ribsky.common.utils.internet.InternetManager
import com.ribsky.common.utils.internet.InternetManagerImpl
import com.ribsky.common.utils.update.AppUpdate
import com.ribsky.common.utils.update.AppUpdateImpl
import org.koin.dsl.module

val commonDi = module {

    factory<InternetManager> {
        InternetManagerImpl(
            context = get()
        )
    }

    factory<AppUpdate> {
        AppUpdateImpl()
    }

    factory<DynamicModule> {
        DynamicModuleImpl()
    }
}
