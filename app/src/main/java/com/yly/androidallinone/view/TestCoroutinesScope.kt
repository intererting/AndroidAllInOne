package com.yly.androidallinone.view

import com.lqd.commonimp.baseview.BaseEmptyActivity
import com.lqd.commonimp.extend.then
import com.yly.androidallinone.R
import kotlinx.coroutines.*

class TestCoroutinesScope : BaseEmptyActivity(R.layout.test_corouties_scope) {
    override fun initView() {
    }

    override fun initListener() {
    }

    override fun initData() {
//        launch(Dispatchers.IO) {
//            repeat(100) {
//                delay(1000)
//                println(it)
//            }
//        }
        async {
            return@async "xxxx"
        }.then({
            println(it)
        }, this)
    }
}