package com.lqd.commonimp.baseview

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import java.lang.reflect.ParameterizedType

abstract class BaseViewModelActivity<VM : ViewModel> : AppCompatActivity() {

    protected lateinit var viewModel: VM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val clazz = javaClass.genericSuperclass
        if (clazz is ParameterizedType) {
            val type = clazz.actualTypeArguments[0]
            viewModel = ViewModelProviders.of(this).get(type.javaClass as Class<VM>)
        }
    }

}