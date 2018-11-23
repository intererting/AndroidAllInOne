package com.yly.androidallinone.base.view

import android.os.Bundle
import androidx.annotation.CallSuper
import androidx.appcompat.app.AppCompatActivity
import com.yly.androidallinone.extends.setStatusBarLightMode
import com.yly.androidallinone.extends.transparentStatusBar

abstract class BaseActivity : AppCompatActivity() {

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        transparentStatusBar()
        setStatusBarLightMode(true)
    }
}