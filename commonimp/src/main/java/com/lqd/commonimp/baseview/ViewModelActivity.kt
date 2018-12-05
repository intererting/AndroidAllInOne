package com.lqd.commonimp.baseview

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.lqd.commonimp.client.BaseViewModel
import com.lqd.commonimp.client.autoCleared
import com.lqd.commonimp.extend.tips
import java.lang.reflect.ParameterizedType

/**
 * 初始化ViewModel的Activity
 */
abstract class ViewModelActivity<VM : BaseViewModel> : WrapperActivity() {

    protected var viewModel by autoCleared<VM>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val type = (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0]
        viewModel = ViewModelProviders.of(this).get(type as Class<VM>)
        viewModel.infoMsgLiveData.observe(this, Observer { msg ->
            msg?.let { tips(it) }
        })
        viewModel.errMsgLiveData.observe(this, Observer { msg ->
            msg?.let { tips(it) }
        })
    }
}