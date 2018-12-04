package com.lqd.commonimp.baseview

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import java.lang.reflect.ParameterizedType

/**
 * 初始化ViewModel的Activity
 */
abstract class BaseViewModelActivity<VM : ViewModel> : AppCompatActivity() {

    protected lateinit var viewModel: VM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val type = (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0]
        viewModel = ViewModelProviders.of(this).get(type as Class<VM>)
    }

}