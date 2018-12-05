package com.lqd.commonimp.baseview

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import com.lqd.commonimp.client.autoCleared

abstract class BaseDataBindingActivity<VM : ViewModel, DB : ViewDataBinding>(@LayoutRes private val layoutRes: Int)
    : BaseViewModelActivity<VM>() {

    protected var mBinding by autoCleared<DB>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, layoutRes)
        initView()
        initListener()
    }
}