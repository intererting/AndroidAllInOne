package com.yly.androidallinone.extends

import android.content.Context
import android.os.Build

/**
 * 是否5.0以上
 */
fun Context.isVersion5Above() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP

/**
 * 是否6.0以上
 */
fun Context.isVersion6Above() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.M

/**
 * 是否7.0以上
 */
fun Context.isVersion7Above() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.N

/**
 * 是否8.0以上
 */
fun Context.isVersion8Above() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.O

/**
 * 获取状态栏的高度
 */
inline val Context.statusBarHeight: Int
    get() {
        var result = 0
        val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            result = resources.getDimensionPixelSize(resourceId)
        }
        return result
    }



