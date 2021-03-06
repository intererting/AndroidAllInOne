package com.lqd.commonimp.cstmview

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.ColorInt
import androidx.fragment.app.DialogFragment
import com.lqd.commonimp.R
import com.lqd.commonimp.extend.screenWidth
import com.lqd.commonimp.extend.visible
import kotlinx.android.synthetic.main.toast_view_alertdialog.view.*

/**
 * 选择弹出框
 */
class MyAlertDialog : DialogFragment() {

    private lateinit var mContext: Context

    private lateinit var rootView: View

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val myDialog = Dialog(mContext, R.style.AlertDialogStyle)
        myDialog.setContentView(rootView)
        myDialog.window?.apply {
            setBackgroundDrawableResource(R.drawable.toast_alert_bg)
            val params = attributes
            params.width = (mContext.screenWidth * 0.75).toInt()
            params.height = ViewGroup.LayoutParams.WRAP_CONTENT
            attributes = params
        }
        return myDialog
    }

    /**
     * 初始化布局
     */
    fun build(context: Context,
              title: String? = null,
              msg: String,
              leftBtnText: String,
              leftClickListener: (() -> Unit)?): MyAlertDialog {
        mContext = context
        rootView = LayoutInflater.from(mContext).inflate(R.layout.toast_view_alertdialog, null, false)
        //设置标题
        rootView.dialogTitle.visible = !title.isNullOrBlank()
        rootView.dialogTitle.text = title
        //设置提示信息
        rootView.dialogMsg.text = msg
        //设置确定按钮
        rootView.leftBtn.setBackgroundResource(R.drawable.alertdialog_single_selector)
        rootView.leftBtn.text = leftBtnText
        rootView.leftBtn.setOnClickListener {
            leftClickListener?.invoke()
            dismiss()
        }
        return this
    }

    /**
     * 初始化布局
     */
    fun build(context: Context,
              title: String? = null,
              msg: String,
              leftBtnText: String,
              leftClickListener: (() -> Unit)?,
              rightBtnText: String,
              rightClickListener: (() -> Unit)?): MyAlertDialog {
        build(context, title, msg, leftBtnText, leftClickListener)
        rootView.leftBtn.setBackgroundResource(R.drawable.alertdialog_left_selector)
        //设置取消按钮
        rootView.rightBtn.visible = true
        rootView.rightBtn.setBackgroundResource(R.drawable.alertdialog_right_selector)
        rootView.rightBtn.text = rightBtnText
        rootView.rightBtn.setOnClickListener {
            rightClickListener?.invoke()
            dismiss()
        }
        return this
    }

    fun setLeftBtnColor(@ColorInt colorInt: Int): MyAlertDialog {
        rootView.leftBtn.setTextColor(colorInt)
        return this
    }

    fun setRightBtnColor(@ColorInt colorInt: Int): MyAlertDialog {
        rootView.rightBtn.setTextColor(colorInt)
        return this
    }
}