package com.yly.androidallinone.base.client

import android.app.Application
import com.alibaba.android.arouter.launcher.ARouter
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
        ARouter.openLog();     // 打印日志
        ARouter.openDebug();   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        ARouter.init(this); // 尽可能早，推荐在Application中初始化
    }

}