package com.yly.androidallinone.util

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Handler
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.ImageSpan
import android.view.Gravity
import android.widget.Toast
import androidx.annotation.DrawableRes
import com.lqd.commonimp.client.BaseApplication
import com.lqd.commonimp.extend.getDrawable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.jetbrains.anko.backgroundColor

@SuppressLint("ShowToast")
object ToastUtil {

    private val mHandler = Handler()
    private val runnable: Runnable = Runnable {
        mStringToast.cancel()
        mImgToast.cancel()
    }

    private val mStringToast by lazy {
        Toast.makeText(BaseApplication.provideInstance(), "", Toast.LENGTH_SHORT).apply {
            setGravity(Gravity.CENTER, 0, 0)
        }
    }

    private val mImgToast by lazy {
        Toast.makeText(BaseApplication.provideInstance(), "", Toast.LENGTH_SHORT).apply {
            setGravity(Gravity.CENTER, 0, 0)
            view.backgroundColor = Color.TRANSPARENT
        }
    }

    fun showStringToast(msg: String) {
        mHandler.removeCallbacks(runnable)
        mStringToast.setText(msg)
        GlobalScope.launch(Dispatchers.Main) {
            mStringToast.show()
        }
        mHandler.postDelayed(runnable, 1500)
    }

    fun showImgToast(@DrawableRes resId: Int) {
        mHandler.removeCallbacks(runnable)
        val msg = SpannableStringBuilder(" ").apply {
            val drawable = resId.getDrawable()
            drawable.setBounds(0, 0, drawable.intrinsicWidth, drawable.intrinsicHeight)
            val imageSpan = ImageSpan(drawable)
            setSpan(imageSpan, 0, length, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
        }
        mImgToast.setText(msg)
        GlobalScope.launch(Dispatchers.Main) {
            mImgToast.show()
        }
        mHandler.postDelayed(runnable, 1500)
    }
}