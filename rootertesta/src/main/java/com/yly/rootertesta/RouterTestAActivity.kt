package com.yly.rootertesta

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import kotlinx.android.synthetic.main.activity_router_test_a.*

@Route(path = "/testa/activity")
class RouterTestAActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_router_test_a)
        startRouter.setOnClickListener {
            ARouter.getInstance().build("/testb/activity").navigation();
        }
    }
}