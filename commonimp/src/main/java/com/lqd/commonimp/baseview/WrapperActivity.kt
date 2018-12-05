package com.lqd.commonimp.baseview

import android.content.Context
import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.lqd.commonimp.client.LiveDataBus
import com.lqd.commonimp.client.autoCleared
import com.lqd.commonimp.extend.dismissLoadingDialog
import com.lqd.commonimp.extend.setStatusBarLightMode
import com.lqd.commonimp.extend.showLoadingDialog
import com.lqd.commonimp.extend.transparentStatusBar
import com.lqd.commonimp.model.LoadingDialogState
import java.util.concurrent.atomic.AtomicBoolean

abstract class WrapperActivity : AppCompatActivity(), ViewInitAction {

    protected var mContext by autoCleared<Context>()

    protected val initFlag = AtomicBoolean(true)

    //LoadingDialog
    private val loadingDialogObserver by lazy {
        Observer<LoadingDialogState> {
            it?.let { dialogState ->
                if (dialogState.isShowDialog) {
                    showLoadingDialog(
                            cancelable = dialogState.cancelable,
                            msg = dialogState.loadingMsg)
                } else {
                    dismissLoadingDialog()
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = this
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        transparentStatusBar()
        setStatusBarLightMode(true)
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus && initFlag.get()) {
            initData()
            initFlag.compareAndSet(true, false)
        }
    }

    override fun onStop() {
        super.onStop()
        LiveDataBus.get().with(localClassName, LoadingDialogState::class.java).removeObserver(loadingDialogObserver)
    }

    override fun onStart() {
        super.onStart()
        LiveDataBus.get().with(localClassName, LoadingDialogState::class.java).observe(this, loadingDialogObserver)
    }
}