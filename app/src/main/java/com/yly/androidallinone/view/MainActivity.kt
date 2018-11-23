package com.yly.androidallinone.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.yly.androidallinone.R
import com.yly.androidallinone.extends.addStatusBarFixView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addStatusBarFixView()
        setContentView(R.layout.activity_main)
    }
}
