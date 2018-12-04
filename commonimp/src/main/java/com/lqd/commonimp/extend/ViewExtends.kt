package com.lqd.commonimp.extend

import android.graphics.drawable.Drawable
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.TransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.lqd.commonimp.image.GlideApp
import org.jetbrains.anko.dip

/**
 * 加载图片
 */
fun ImageView.loadImage(res: Any?
                        , options: RequestOptions? = null
                        , transitionOptions: TransitionOptions<*, Drawable>? = null) {
    post {
        var mOptions = options
        if (null == mOptions) {
            mOptions = RequestOptions()
        }
        mOptions.placeholder(context.loadImageDefault(width, height))
        mOptions.error(context.loadImageDefault(width, height))
        val glideRequest = GlideApp.with(context)
                .load(res)
                .apply(mOptions)
                .centerCrop()
                .override(width, height)
        transitionOptions?.let {
            glideRequest.transition(it)
        }
        glideRequest.into(this)
    }
}

inline var View.visible: Boolean
    get() = visibility == View.VISIBLE
    set(value) {
        visibility = if (value) View.VISIBLE else View.GONE
    }

inline var View.bottomMargin: Int
    get():Int {
        return (layoutParams as ViewGroup.MarginLayoutParams).bottomMargin
    }
    set(value) {
        (layoutParams as ViewGroup.MarginLayoutParams).bottomMargin = value
    }

inline var View.topMargin: Int
    get():Int {
        return (layoutParams as ViewGroup.MarginLayoutParams).topMargin
    }
    set(value) {
        (layoutParams as ViewGroup.MarginLayoutParams).topMargin = value
    }


inline var View.rightMargin: Int
    get():Int {
        return (layoutParams as ViewGroup.MarginLayoutParams).rightMargin
    }
    set(value) {
        (layoutParams as ViewGroup.MarginLayoutParams).rightMargin = value
    }

inline var View.leftMargin: Int
    get():Int {
        return (layoutParams as ViewGroup.MarginLayoutParams).leftMargin
    }
    set(value) {
        (layoutParams as ViewGroup.MarginLayoutParams).leftMargin = value
    }

inline var View.leftPadding: Int
    get() = paddingLeft
    set(value) = setPadding(value, paddingTop, paddingRight, paddingBottom)

inline var View.topPadding: Int
    get() = paddingTop
    set(value) = setPadding(paddingLeft, value, paddingRight, paddingBottom)

inline var View.rightPadding: Int
    get() = paddingRight
    set(value) = setPadding(paddingLeft, paddingTop, value, paddingBottom)

inline var View.bottomPadding: Int
    get() = paddingBottom
    set(value) = setPadding(paddingLeft, paddingTop, paddingRight, value)

fun TextView.setDrawableLeft(resId: Int) {
    val drawable = resId.getDrawable()
    this.compoundDrawablePadding = dip(5.0f)
    drawable.setBounds(0, 0, drawable.minimumWidth, drawable.minimumHeight)
    this.setCompoundDrawables(drawable, null, null, null)
}

fun TextView.setDrawableRight(resId: Int) {
    val drawable = resId.getDrawable()
    drawable.setBounds(0, 0, drawable.minimumWidth, drawable.minimumHeight)
    this.compoundDrawablePadding = dip(5.0f)
    this.setCompoundDrawables(null, null, drawable, null)
}

fun TextView.setDrawableTop(resId: Int, drawablePadding: Float = 5.0f) {
    val drawable = resId.getDrawable()
    this.compoundDrawablePadding = dip(drawablePadding)
    drawable.setBounds(0, 0, drawable.minimumWidth, drawable.minimumHeight)
    this.setCompoundDrawables(null, drawable, null, null)
}

fun TextView.setDrawableBottom(resId: Int) {
    val drawable = resId.getDrawable()
    this.compoundDrawablePadding = dip(5.0f)
    drawable.setBounds(0, 0, drawable.minimumWidth, drawable.minimumHeight)
    this.setCompoundDrawables(null, null, null, drawable)
}
