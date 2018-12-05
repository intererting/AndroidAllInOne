package com.lqd.commonimp.baseview

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import java.lang.reflect.ParameterizedType

/**
 * 初始化ViewModel的Activity
 */
abstract class BaseViewModelActivity<VM : ViewModel> : AppCompatActivity() {

    protected val viewModel by lazy {
        val type = (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0]
        ViewModelProviders.of(this).get(type as Class<VM>)
    }
}