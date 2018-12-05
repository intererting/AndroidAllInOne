package com.lqd.commonimp.baseview

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.lqd.commonimp.client.autoCleared

abstract class BaseDataBindingActivity<VM : BaseViewModel, DB : ViewDataBinding>(@LayoutRes private val layoutRes: Int)
    : ViewModelActivity<VM>() {

    protected var mBinding by autoCleared<DB>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, layoutRes)
        initView()
        initListener()
    }
}