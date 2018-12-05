package com.lqd.commonimp.baseview

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.lifecycle.ViewModel

abstract class BaseLayoutActivity<VM : ViewModel>(@LayoutRes private val layoutRes: Int)
    : BaseViewModelActivity<VM>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutRes)
        initView()
        initListener()
    }
}