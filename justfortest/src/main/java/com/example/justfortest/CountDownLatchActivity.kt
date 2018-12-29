package com.example.justfortest

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_countdown.*
import java.util.concurrent.CountDownLatch

class CountDownLatchActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_countdown)

        val countDownLatch = CountDownLatch(1)
        val threadA = Thread(Runnable {
            Thread.yield()
            println("before countDownLatch")
            try {
                countDownLatch.await()
            } catch (e: Exception) {
            }
            println("after countDownLatch")

        })
        threadA.start()
        countDown.setOnClickListener {
            countDownLatch.countDown()
        }
        interrupt.setOnClickListener {
            try {
                threadA.interrupt()
            } catch (e: Exception) {
            }
        }
    }

}