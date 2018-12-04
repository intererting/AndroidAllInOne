package com.lqd.commonimp.extend

import android.app.Activity
import android.view.View
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.lqd.commonimp.cstmview.MyActionSheet
import java.util.*

fun Fragment.showImgToast(@DrawableRes resId: Int) = activity?.showImgToast(resId)

/**
 * 加载对话框
 */
fun Fragment.showLoadingDialog(cancelable: Boolean = false, msg: String? = null) = activity?.showLoadingDialog(cancelable, msg)

/**
 * 选择弹出框
 */
fun Fragment.showConfirmDialog(title: String? = null, msg: String, leftBtnText: String = "确定", leftClickListener: (() -> Unit)? = null) =
        activity?.showConfirmDialog(title, msg, leftBtnText, leftClickListener)


fun Fragment.showConfirmDialog(title: String? = null, msg: String, leftBtnText: String = "取消", leftClickListener: (() -> Unit)? = null
                               , rightBtnText: String = "确定", rightClickListener: (() -> Unit)? = null) =
        activity?.showConfirmDialog(title, msg, leftBtnText, leftClickListener, rightBtnText, rightClickListener)

/**
 * 底部弹出框
 */
fun Fragment.showActionSheet(title: String?, items: List<MyActionSheet.ActionSheetItem>) = activity?.showActionSheet(title, items)

fun Fragment.log(info: String) = activity?.log(info)

/**
 * 时间选择框
 */
fun Fragment.showDateTimeDialog(selectedDate: Calendar = Calendar.getInstance(), startDate: Calendar? = null, endDate: Calendar? = null
                                , type: BooleanArray = booleanArrayOf(true, true, true, false, false, false), clickListener: (Date, View?) -> Unit) =
        activity?.showDateTimeDialog(selectedDate, startDate, endDate, type, clickListener)

/**
 * 取消正在加载对话框
 */
fun Fragment.dismissLoadingDialog() = activity?.dismissLoadingDialog()

/**
 * 添加Fragment
 */
fun Fragment.replaceFragmentInFragment(fragment: Fragment, frameId: Int) {
    val transaction = childFragmentManager.beginTransaction()
    transaction.replace(frameId, fragment)
    transaction.commit()
}

inline fun Fragment.delayWithUI(delayMillis: Long, crossinline block: Activity.() -> Unit) //
        = (activity as? AppCompatActivity)?.delayWithUI(delayMillis, block)