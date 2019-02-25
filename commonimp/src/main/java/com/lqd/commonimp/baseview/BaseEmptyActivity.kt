package com.lqd.commonimp.baseview

import android.os.Bundle
import androidx.annotation.LayoutRes

abstract class BaseEmptyActivity(@LayoutRes private val layoutRes: Int) : WrapperActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutRes)
        initView()
        initListener()
    }
}