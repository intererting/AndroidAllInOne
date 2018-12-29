package com.yly.androidallinone.test.other

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.yly.androidallinone.R
import kotlinx.android.synthetic.main.activity_countdown.*
import java.util.concurrent.CountDownLatch

class CountDownActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_countdown)

        val countDownLatch = CountDownLatch(1)
        val threadA = Thread(Runnable {
            println("before countDownLatch")
            countDownLatch.await()
            println("after countDownLatch")

        })
        threadA.start()
        countDown.setOnClickListener {
            countDownLatch.countDown()
        }
        interrupt.setOnClickListener {
            threadA.interrupt()
        }
    }
}