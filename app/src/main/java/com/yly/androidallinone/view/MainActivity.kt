package com.yly.androidallinone.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.viewpager.widget.ViewPager
import com.yly.androidallinone.R
import com.yly.androidallinone.extends.addStatusBarFixView
import org.jetbrains.anko.*
import org.jetbrains.anko.constraint.layout.constraintLayout

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addStatusBarFixView()

        setContentView(R.layout.activity_main)
        constraintLayout {

        }
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
