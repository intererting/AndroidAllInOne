package com.yly.androidallinone.view.coroutines

import android.os.Bundle
import com.yly.androidallinone.base.view.BaseActivity
import kotlinx.coroutines.*
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick

class CoroutinesActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        testContext()

        GlobalScope.launch(Dispatchers.Main) {
            //不会阻塞UI
//            coroutineScope {
//                delay(10000)
//            }

//            coroutineScope {
//                //coroutineScope会阻塞协成
//                delay(2000)
//                println("first")
//            }
//
//            GlobalScope.launch {
//                println("second")
//            }
        }

//        GlobalScope.launch(Dispatchers.Main) {
//            println("  GlobalScope.launch  ${Thread.currentThread()}")
//            test()
//        }

        verticalLayout {
            val name = editText()
            button("Say Hello") {
                onClick { toast("Hello, ${name.text}!") }
            }
        }

    }

    fun testContext() {
        GlobalScope.launch {
            println("GlobalScope.launch  ${Thread.currentThread()}")
        }
        GlobalScope.async {
            println("GlobalScope.async  ${Thread.currentThread()}")
        }
        GlobalScope.launch(Dispatchers.Default) {
            println("GlobalScope.Default  ${Thread.currentThread()}")
        }
        GlobalScope.launch(Dispatchers.Main) {
            launch {
                println("  GlobalScope.launch(Dispatchers.IO)-GlobalScope.launch  ${Thread.currentThread()}")
            }
        }
    }


    private suspend fun test() {
        val result = GlobalScope.async(Dispatchers.Main) {
            println("  GlobalScope.async  ${Thread.currentThread()}")
            delay(10000)
            return@async "test"
        }
        println(result.await())

//        val result = GlobalScope.launch(Dispatchers.Main) {
//            println("  GlobalScope.async  ${Thread.currentThread()}")
//            delay(10000)
//        }
//        println(result.join())
    }
}