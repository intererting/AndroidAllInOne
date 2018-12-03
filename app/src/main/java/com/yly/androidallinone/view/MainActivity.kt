package com.yly.androidallinone.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.viewpager.widget.ViewPager
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.yly.androidallinone.R
import com.yly.androidallinone.extends.addStatusBarFixView
import com.yly.rootertesta.RouterTestAActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.*
import org.jetbrains.anko.constraint.layout.constraintLayout

@Route(path = "/test/activity1")
class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addStatusBarFixView()

        setContentView(R.layout.activity_main)
        viewCache.setData()

        startRouter.setOnClickListener {
            ARouter.getInstance().build("/testa/activity").navigation();
//            startActivity(intentFor<RouterTestAActivity>())
        }
//        constraintLayout {
//
//        }
//        verticalLayout {
//            padding = dip(30)
//            editText {
//                hint = "Name"
//                textSize = 24f
//            }
//            editText {
//                hint = "Password"
//                textSize = 24f
//            }
//            button("Login") {
//                textSize = 26f
//            }
//        }
    }
}

//class MyActivityUI : AnkoComponent<MainActivity> {
//    override fun createView(ui: AnkoContext<MainActivity>) = with(ui) {
//        verticalLayout {
//            val name = editText()
//            button("Say Hello") {
//                onClick { ctx.toast("Hello, ${name.text}!") }
//            }
//        }
//    }
//}
