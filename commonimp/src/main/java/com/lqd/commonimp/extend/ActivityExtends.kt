package com.lqd.commonimp.extend

import android.annotation.TargetApi
import android.app.Activity
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.Rect
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.FrameLayout
import androidx.annotation.ColorRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Lifecycle
import com.bigkoo.pickerview.builder.TimePickerBuilder
import com.bigkoo.pickerview.listener.OnTimeSelectListener
import com.lqd.commonimp.R
import com.lqd.commonimp.cstmview.LoadingFragment
import com.lqd.commonimp.cstmview.MyActionSheet
import com.lqd.commonimp.cstmview.MyAlertDialog
import java.lang.ref.WeakReference
import java.util.*

/**
 * 屏幕截图
 */
fun Activity.screenShot(isDeleteStatusBar: Boolean = true): Bitmap {
    val decorView = window.decorView
    decorView.isDrawingCacheEnabled = true
    decorView.buildDrawingCache()
    val bmp = decorView.drawingCache
    val ret: Bitmap
    ret = if (isDeleteStatusBar) {
        Bitmap.createBitmap(bmp, 0, statusBarHeight, screenWidth, screenHeight - statusBarHeight)
    } else {
        Bitmap.createBitmap(bmp, 0, 0, screenWidth, screenHeight)
    }
    decorView.destroyDrawingCache()
    return ret
}

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
 * 显示选择弹出框
 */
fun FragmentActivity.showConfirmDialog(title: String? = null,
                                       msg: String,
                                       leftBtnText: String = "取消",
                                       leftClickListener: (() -> Unit)? = null,
                                       rightBtnText: String = "确定",
                                       rightClickListener: (() -> Unit)? = null) {
    MyAlertDialog().build(this, title, msg, leftBtnText, leftClickListener, rightBtnText, rightClickListener)
            .show(supportFragmentManager, "confirmDialog")
}

/**
 * 显示单选弹出框
 */
fun FragmentActivity.showSingleConfirmDialog(title: String? = null,
                                             msg: String,
                                             leftBtnText: String = "确定",
                                             leftClickListener: (() -> Unit)? = null) {
    MyAlertDialog().build(this, title, msg, leftBtnText, leftClickListener)
            .show(supportFragmentManager, "confirmDialog")
}

/**
 * 底部弹出框
 */
fun FragmentActivity.showActionSheet(title: String?, items: List<MyActionSheet.ActionSheetItem>) {
    MyActionSheet().build(this, title, items).show(supportFragmentManager, "bottomDialog")
}

/**
 * 时间选择框
 */
fun Activity.showDateTimeDialog(selectedDate: Calendar = Calendar.getInstance(), startDate: Calendar? = null, endDate: Calendar? = null
                                , type: BooleanArray = booleanArrayOf(true, true, true, false, false, false), clickListener: (Date, View?) -> Unit) {
    val builder = TimePickerBuilder(this, OnTimeSelectListener { date, v ->
        clickListener(date, v)
    }).setDate(selectedDate)
            .setType(type)
            .setCancelColor(R.color.ios_blue.getColor())
            .setSubmitColor(R.color.ios_blue.getColor())
            .setSubCalSize(15)
    if (null != startDate && null != endDate) {
        builder.setRangDate(startDate, endDate)
    }
    builder.build().show()
}

/**
 * 是否竖屏
 */
fun Activity.isPortrait(): Boolean {
    return resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT
}

/**
 * 是否横屏
 */
fun Activity.isLandscape(): Boolean {
    return resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
}


/**
 * 设置竖屏
 */
fun Activity.setPortrait() {
    this.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
}

/**
 * 设置横屏
 */
fun Activity.setLandscape() {
    this.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
}

/**
 * 设置全屏
 */
fun Activity.setFullScreen() {
    this.window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN or WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
}

/**
 * 显示软键盘
 */
fun Activity.showKeyboard() {
    val imm: InputMethodManager? = getSystemService(Activity.INPUT_METHOD_SERVICE) as? InputMethodManager
    imm?.let {
        var view = this.currentFocus
        if (view == null) {
            view = View(this)
            view.isFocusable = true
            view.isFocusableInTouchMode = true
            view.requestFocus()
        }
        imm.showSoftInput(view, InputMethodManager.SHOW_FORCED)
    }

}

/**
 * 隐藏软键盘
 */
fun Activity.hideKeyboard() {
    val imm: InputMethodManager? = getSystemService(Activity.INPUT_METHOD_SERVICE) as? InputMethodManager
    imm?.let {
        var view = this.currentFocus
        if (view == null) {
            view = View(this)
        }
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}

/**
 * 添加Fragment
 */
fun AppCompatActivity.replaceFragmentInActivity(fragment: Fragment, containerId: Int, tag: String = "") {
    val transaction = supportFragmentManager.beginTransaction()
    transaction.replace(containerId, fragment, tag)
    transaction.commit()
}

/**
 * 添加Fragment
 */
fun FragmentActivity.addFragmentInActivity(fragment: Fragment, tag: String) {
    val transaction = supportFragmentManager.beginTransaction()
    transaction.add(fragment, tag)
    transaction.commit()
}

/**
 * 加载对话框
 */
fun FragmentActivity.showLoadingDialog(cancelable: Boolean = false, msg: String? = null) {
    LoadingFragment.newInstance(cancelable, msg).showNow(supportFragmentManager, "loading")
}

/**
 * 取消正在加载对话框
 */
fun FragmentActivity.dismissLoadingDialog() {
    val loadingFragment = supportFragmentManager.findFragmentByTag("loading") as? LoadingFragment
    loadingFragment?.dismiss()
}

/**
 * 延迟执行
 */
inline fun AppCompatActivity.delayWithUI(delayMillis: Long, crossinline block: AppCompatActivity.() -> Unit) {
    val ref = WeakReference(this)
    val handler = Handler(Looper.getMainLooper())
    handler.postDelayed({
        if (lifecycle.currentState.isAtLeast(Lifecycle.State.CREATED)) {
            ref.get()?.block()
        }
    }, delayMillis)
}

private var sContentViewInvisibleHeightPre: Int = 0
private var onGlobalLayoutListener: ViewTreeObserver.OnGlobalLayoutListener? = null
//private var onSoftInputChangedListener: OnSoftInputChangedListener? = null

private fun Activity.getContentViewInvisibleHeight(): Int {
    val contentView = this.findViewById<FrameLayout>(android.R.id.content)
    val contentViewChild = contentView.getChildAt(0)
    val outRect = Rect()
    contentViewChild.getWindowVisibleDisplayFrame(outRect)
    return contentViewChild.bottom - outRect.bottom
}

/**
 * Register soft input changed listener.
 *
 * @param activity The activity.
 * @param listener The soft input changed listener.
 */
fun Activity.registerSoftInputChangedListener(listener: ((height: Int) -> Unit)?) {
    val flags = window.attributes.flags
    if (flags and WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS != 0) {
        window.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
    }
    val contentView = findViewById<FrameLayout>(android.R.id.content)
    sContentViewInvisibleHeightPre = getContentViewInvisibleHeight()
    onGlobalLayoutListener = ViewTreeObserver.OnGlobalLayoutListener {
        listener?.let {
            val height = getContentViewInvisibleHeight()
            if (sContentViewInvisibleHeightPre != height) {
                it.invoke(height)
                sContentViewInvisibleHeightPre = height
            }
        }
    }
    contentView.viewTreeObserver
            .addOnGlobalLayoutListener(onGlobalLayoutListener)
}

/**
 * Register soft input changed listener.
 *
 * @param activity The activity.
 */
@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
fun Activity.unregisterSoftInputChangedListener() {
    val contentView = findViewById<View>(android.R.id.content)
    contentView.viewTreeObserver.removeOnGlobalLayoutListener(onGlobalLayoutListener)
    onGlobalLayoutListener = null
}