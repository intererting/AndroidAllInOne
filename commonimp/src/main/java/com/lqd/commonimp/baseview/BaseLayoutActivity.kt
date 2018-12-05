package com.lqd.commonimp.baseview

import android.os.Bundle
import androidx.annotation.LayoutRes

abstract class BaseLayoutActivity<VM : BaseViewModel>(@LayoutRes private val layoutRes: Int)
    : ViewModelActivity<VM>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutRes)
        initView()
        initListener()
    }
}