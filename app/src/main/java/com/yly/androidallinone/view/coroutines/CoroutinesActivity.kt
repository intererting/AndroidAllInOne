package com.yly.androidallinone.view.coroutines

import android.os.Bundle
import com.yly.androidallinone.base.view.BaseActivity
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.selects.select
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import java.io.IOException
import java.lang.Exception

class CoroutinesActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        testContext()

//        testException()

        testChannel()

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

    private fun testException() {
        val handler = CoroutineExceptionHandler { _, throwable ->
            println(throwable.message)
        }
        GlobalScope.launch(handler + CoroutineName("异常")) {
            try {
                throw IOException("测试")
            } catch (e: Exception) {
                println("在try里面捕获异常")
                throw  e
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

    private fun testChannel() {
        GlobalScope.launch {

            //            val numbers = produceNumbers()
//            launchProcessor(1, numbers)
            val a = produce<String> {
                repeat(4) { send("Hello $it") }
            }
            val b = produce<String> {
                repeat(4) { send("World $it") }
            }
            repeat(8) {
                // print first eight results
                println(selectAorB(a, b))
            }

            coroutineContext.cancelChildren()

        }
    }
}

@ExperimentalCoroutinesApi
fun CoroutineScope.produceNumbers() = produce<Int> {
    var x = 1 // start from 1
    while (true) {
        //send和offer的区别
        println(offer(x++)) // produce next
//        send(x++)
        delay(1000) // wait 0.1s
    }
}

fun CoroutineScope.launchProcessor(id: Int, channel: ReceiveChannel<Int>) = launch {
    for (msg in channel) {
        if (msg == 10) {
            delay(10000)
        }
        println("Processor #$id received $msg")
    }
}

suspend fun selectAorB(a: ReceiveChannel<String>, b: ReceiveChannel<String>): String =
        select<String> {
            a.onReceiveOrNull { value ->
                if (value == null)
                    "Channel 'a' is closed"
                else
                    "a -> '$value'"
            }
            b.onReceiveOrNull { value ->
                if (value == null)
                    "Channel 'b' is closed"
                else
                    "b -> '$value'"
            }
        }