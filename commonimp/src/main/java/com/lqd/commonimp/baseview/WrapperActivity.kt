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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import java.util.concurrent.atomic.AtomicBoolean
import kotlin.coroutines.CoroutineContext

abstract class WrapperActivity : AppCompatActivity(), ViewInitAction, CoroutineScope {

    protected var mContext by autoCleared<Context>()
    private lateinit var job: Job
    private val initFlag = AtomicBoolean(true)

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

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
        job = Job()
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

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }
}