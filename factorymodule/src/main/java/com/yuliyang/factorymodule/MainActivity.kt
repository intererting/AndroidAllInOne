package com.yuliyang.factorymodule

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.yuliyang.factorymodule.ws.TestViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: TestViewModel
    private var first = true

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        savedInstanceState?.apply {
            first = getBoolean("first")
        }
        println(first)
        viewModel = ViewModelProviders.of(this).get(TestViewModel::class.java)
        viewModel.resultLD.observe(this, Observer {
            it?.let {
                println(it)
            }
        })
        if (first) {
            viewModel.test(1)
            first = false
        }
        startForegroundService(Intent(this, TestService::class.java))
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.apply {
            putBoolean("first", first)
        }
    }
}
