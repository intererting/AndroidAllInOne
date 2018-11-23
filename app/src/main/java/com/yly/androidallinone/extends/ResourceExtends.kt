package com.yly.androidallinone.extends

import android.graphics.drawable.Drawable
import com.yly.androidallinone.base.client.One

@Suppress("DEPRECATION")
fun Int.getColor(): Int {
    val mContext = One.provideInstance()
    return if (mContext.isVersion6Above()) {
        mContext.resources.getColor(this, mContext.theme)
    } else {
        mContext.resources.getColor(this)
    }
}

fun Int.getDimension(): Float {
    val mContext = One.provideInstance()
    return mContext.resources.getDimension(this)
}

fun Int.getString(): String {
    val mContext = One.provideInstance()
    return mContext.resources.getString(this)
}

@Suppress("DEPRECATION")
fun Int.getDrawable(): Drawable {
    val mContext = One.provideInstance()
    return if (mContext.isVersion6Above()) {
        mContext.resources.getDrawable(this, mContext.theme)
    } else {
        mContext.resources.getDrawable(this)
    }
}