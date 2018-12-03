package com.lqd.httpclient

import androidx.lifecycle.Observer

abstract class MyObserverV<T>() : Observer<Resource<T>> {

    override fun onChanged(t: Resource<T>?) {
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

    open fun onLoading(data: T?, loadingMsg: String?) {
    }

    abstract fun onComplete(data: T?, msg: String?)

    open fun onFailed() {}
}