package com.yly.androidallinone.view.kotlin

import android.os.Bundle
import com.lqd.commonimp.extend.addStatusBarFixView
import com.yly.androidallinone.R
import com.yly.androidallinone.base.view.BaseActivity
import kotlinx.android.synthetic.main.activity_view_cache.*

class ViewCacheActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_cache)
        addStatusBarFixView()
        setData.setOnClickListener {
            a()
        }
    }


}

fun ViewCacheActivity.a() {
    textView.text = "Hidden view"
}