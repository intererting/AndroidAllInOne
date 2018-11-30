package com.yly.androidallinone.view.thread

import android.os.Bundle
import com.yly.androidallinone.base.view.BaseActivity
import org.jetbrains.anko.button
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.verticalLayout
import java.util.concurrent.CyclicBarrier

class CyclicBarrierActivity : BaseActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val barrier = CyclicBarrier(2) {
            println("任务完成")
        }

        verticalLayout {
            button("1号ready") {
                onClick {
                    println("1号ready")
                    Thread1(barrier).start()
                }
            }
            button("2号ready") {
                onClick {
                    println("2号ready")
                    Thread2(barrier).start()
                }
            }
        }

    }

    class Thread1(val barrier: CyclicBarrier) : Thread() {
        override fun run() {
            super.run()
            Thread.sleep(5000)
            barrier.await()
            println("1号完成")
        }
    }

    class Thread2(val barrier: CyclicBarrier) : Thread() {
        override fun run() {
            super.run()
            Thread.sleep(10000)
            barrier.await()
            println("2号完成")
        }
    }

}