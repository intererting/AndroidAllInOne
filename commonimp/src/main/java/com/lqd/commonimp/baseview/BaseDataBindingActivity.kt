package com.lqd.commonimp.baseview

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel

abstract class BaseDataBindingActivity<VM : ViewModel, DB : ViewDataBinding>(@LayoutRes private val layoutRes: Int)
    : BaseViewModelActivity<VM>(), ViewInitAction {

    protected lateinit var mBinding: DB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, layoutRes)
        initView()
        initData()
        initListener()
    }
}