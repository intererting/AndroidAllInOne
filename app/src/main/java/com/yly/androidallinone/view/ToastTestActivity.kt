package com.yly.androidallinone.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.yly.androidallinone.R
import com.yly.androidallinone.util.ToastUtil
import kotlinx.android.synthetic.main.activity_toast_test.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class ToastTestActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_toast_test)
        showToast.setOnClickListener {
            //            ToastUtil.showTips(System.currentTimeMillis().toString())
//            ToastUtil.showStringToast("hahaha")
            ToastUtil.showImgToast(R.drawable.network_anomaly)
            val job: Job = GlobalScope.launch {

            }
        }
    }
}