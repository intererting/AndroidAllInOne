package com.yly.androidallinone.view

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.alibaba.android.arouter.facade.annotation.Route
import com.lqd.commonimp.extend.addStatusBarFixView
import com.yly.androidallinone.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.actor
import org.jetbrains.anko.intentFor

@Route(path = "/test/activity1")
class MainActivity : AppCompatActivity() {


    @ObsoleteCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addStatusBarFixView()

        Thread(Runnable {

        }).start()

        println(cacheDir.parentFile.absolutePath)

        setContentView(R.layout.activity_main)
        viewCache.setData()

//        startRouter.setOnClickListener {
//            ARouter.getInstance().build("/testa/activity").navigation();
////            startActivity(intentFor<RouterTestAActivity>())
//        }
        startRouter.onClickStart {
            //            delay(2000)
//            println("xxxxxxxxxxxxxxxxxx")
            startActivity(intentFor<TestCoroutinesScope>())
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

@ObsoleteCoroutinesApi
fun View.onClickStart(action: suspend () -> Unit) {
    val eventActor = GlobalScope.actor<Unit>(Dispatchers.Main) {
        for (event in channel) {
            action()
        }
    }
    setOnClickListener {
        eventActor.offer(Unit)
    }
}

