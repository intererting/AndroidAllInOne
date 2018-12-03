package com.yly.androidallinone.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.yly.androidallinone.R
import kotlinx.android.synthetic.main.test_view_cache.view.*

class ViewCache @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0)
    : ConstraintLayout(context, attrs, defStyleAttr) {

    init {
        LayoutInflater.from(context).inflate(R.layout.test_view_cache, this, true)
    }
}

fun ViewCache.setData() {
    testCache.text = "xxxxx"
}