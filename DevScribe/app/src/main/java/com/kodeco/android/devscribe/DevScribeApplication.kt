package com.kodeco.android.devscribe

import android.app.Application
import com.kodeco.android.devscribe.di.appModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class DevScribeApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@DevScribeApplication)
            modules(appModules)
        }
    }
}