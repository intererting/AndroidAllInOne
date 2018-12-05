package com.lqd.httpclient

import androidx.annotation.CallSuper
import androidx.lifecycle.Observer
import com.lqd.commonimp.client.BaseApplication
import com.lqd.commonimp.client.LiveDataBus
import com.lqd.commonimp.model.LoadingDialogState

abstract class MyNetWorkObserver<T>(private val showLoading: Boolean = false
                                    , private val cancelable: Boolean = false) : Observer<Resource<T>> {

    final override fun onChanged(t: Resource<T>?) {
        if (t == null) {
            onComplete(null, null)
        } else {
            when (t.status) {
                Status.SUCCESS -> {
                    onComplete(t.data, t.msg)
                }
                Status.LOADING -> {
                    onLoading(t.data, t.msg)
                }
                Status.FAILED -> {
                    onComplete(t.data, t.msg)
                    onFailed()
                }
            }
        }
    }

    @CallSuper
    open fun onLoading(data: T?, loadingMsg: String?) {
        if (showLoading) {
            LiveDataBus.get().with(BaseApplication.provideInstance().topActivityInStack.localClassName)
                    .postValue(LoadingDialogState(isShowDialog = true
                            , loadingMsg = loadingMsg
                            , cancelable = cancelable))
        }
    }

    @CallSuper
    open fun onComplete(data: T?, msg: String?) {
        LiveDataBus.get().with(BaseApplication.provideInstance().topActivityInStack.localClassName)
                .postValue(LoadingDialogState(isShowDialog = false))
    }

    open fun onFailed() {}
}