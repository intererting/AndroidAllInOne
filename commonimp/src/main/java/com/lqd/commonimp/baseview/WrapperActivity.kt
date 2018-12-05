package com.lqd.commonimp.baseview

import android.content.Context
import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.lqd.commonimp.client.autoCleared
import com.lqd.commonimp.extend.setStatusBarLightMode
import com.lqd.commonimp.extend.transparentStatusBar
import java.util.concurrent.atomic.AtomicBoolean

abstract class WrapperActivity : AppCompatActivity(), ViewInitAction {

    protected var mContext by autoCleared<Context>()

    protected val initFlag = AtomicBoolean(true)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = this
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        transparentStatusBar()
        setStatusBarLightMode(true)
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus && initFlag.get()) {
            initData()
            initFlag.compareAndSet(true, false)
        }
    }
}