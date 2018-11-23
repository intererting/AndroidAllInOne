package com.yly.androidallinone.base.client

import android.app.Application
import kotlin.properties.Delegates

class One : Application() {
    companion object {

        private var instance: One by Delegates.notNull()

        fun provideInstance(): One {
            return instance
        }
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

}