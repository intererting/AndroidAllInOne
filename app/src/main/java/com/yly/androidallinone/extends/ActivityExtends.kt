package com.yly.androidallinone.extends

import android.app.Activity
import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.annotation.ColorRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import com.yly.androidallinone.R


/**
 * 透明状态栏模式
 */
fun Activity.initTranslucentStatus(statusColor: Int = R.color.alpha_status_bar) {
    transparentStatusBar()
    setStatusBarLightMode(true)
    addStatusBarFixView(statusColor)
}

private const val TAG_COLOR = "TAG_COLOR"

/**
 * 设置透明状态栏颜色
 *
 * @param usrStatusBar 状态栏是否占位
 */
fun Activity.addStatusBarFixView(@ColorRes statusColor: Int = R.color.alpha_status_bar
                                 , usrStatusBar: Boolean = true) {
    val contentView = findViewById<ViewGroup>(android.R.id.content)
    contentView.getChildAt(0)?.fitsSystemWindows = usrStatusBar
    val fakeStatusBarView = contentView.findViewWithTag<View>(TAG_COLOR)
    if (fakeStatusBarView != null) {
        fakeStatusBarView.setBackgroundColor(statusColor.getColor())
    } else {
        val statusBarView = View(this)
        statusBarView.tag = TAG_COLOR
        val lp = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, statusBarHeight)
        statusBarView.setBackgroundColor(statusColor.getColor())
        contentView.addView(statusBarView, lp)
    }
}

/**
 * 设置高亮模式
 */
fun Activity.setStatusBarLightMode(isLightMode: Boolean) {
    if (isVersion6Above()) {
        val decorView = window.decorView
        var vis = decorView.systemUiVisibility
        vis = if (isLightMode) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            vis or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        } else {
            vis and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv()
        }
        decorView.systemUiVisibility = vis
    }
}

/**
 * 透明状态栏模式
 */
fun Activity.transparentStatusBar() {
    if (isVersion5Above()) {
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        val option = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        window.decorView.systemUiVisibility = option
        window.statusBarColor = Color.TRANSPARENT
    } else {
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
    }
}

/**
 * 延迟执行
 */
inline fun AppCompatActivity.delayWithUI(delayMillis: Long, crossinline block: AppCompatActivity.() -> Unit) {
    val ref = java.lang.ref.WeakReference(this)
    val handler = android.os.Handler(android.os.Looper.getMainLooper())
    handler.postDelayed({
        if (lifecycle.currentState.isAtLeast(Lifecycle.State.CREATED)) {
            ref.get()?.block()
        }
    }, delayMillis)
}
