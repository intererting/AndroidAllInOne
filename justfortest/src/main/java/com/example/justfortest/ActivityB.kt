package com.example.justfortest

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.justfortest.R

class ActivityB : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        println("activityB onCreate")
        setContentView(R.layout.activity_b)
    }

    override fun onStart() {
        super.onStart()
        println("activityB onStart")
    }

    override fun onResume() {
        super.onResume()
        println("activityB onResume")
    }

    override fun onPause() {
        super.onPause()
        println("activityB onPause")
    }

    override fun onStop() {
        super.onStop()
        println("activityB onStop")
    }
}