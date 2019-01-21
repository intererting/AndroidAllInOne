package com.example.justfortest

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import androidx.appcompat.app.AppCompatActivity
import com.example.justfortest.R

class ActivityB : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        println("activityB onCreate")
        setContentView(R.layout.activity_b)

        val intent = Intent(this, ServiceTest::class.java)
        bindService(intent, object : ServiceConnection {
            override fun onServiceDisconnected(name: ComponentName?) {
                println("onServiceDisconnected")
            }

            override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
                println("onServiceConnected")
            }

        }, Context.BIND_AUTO_CREATE)
    }

    override fun onStart() {
        super.onStart()
//        println("activityB onStart")
    }

    override fun onResume() {
        super.onResume()
//        println("activityB onResume")
    }

    override fun onPause() {
        super.onPause()
//        println("activityB onPause")
    }

    override fun onStop() {
        super.onStop()
//        println("activityB onStop")
    }
}