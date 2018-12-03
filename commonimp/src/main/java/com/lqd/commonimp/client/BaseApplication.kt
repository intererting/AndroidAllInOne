package com.lqd.commonimp.client

import android.app.Application
import kotlin.properties.Delegates

open class BaseApplication : Application() {

    companion object {

        @JvmStatic
        private var instance: BaseApplication by Delegates.notNull()

        fun provideInstance(): BaseApplication {
            return instance
        }
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}