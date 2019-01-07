package com.example.justfortest

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.io.BufferedOutputStream
import java.io.File
import java.io.FileOutputStream

class ActivityA : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_a)
        findViewById<TextView>(R.id.start).setOnClickListener {
            startActivity(Intent(this, ActivityB::class.java))
        }

        startWriteLog()
    }

    private fun startWriteLog() {
        Thread(Runnable {
            val process = Runtime.getRuntime().exec(arrayOf("logcat", "-s", "System.out"))
            val input = process.inputStream
            val output = File(cacheDir, "logtest.txt")
            if (!output.exists()) {
                output.createNewFile()
            }
            val os = BufferedOutputStream(FileOutputStream(output))
            val byteArray = ByteArray(1024)
            var hasRead = -1
            while (input.read(byteArray).also {
                        hasRead = it
                    } != -1) {
                os.write(byteArray, 0, hasRead)
            }
        }).start()
    }

    override fun onPause() {
        super.onPause()
        println("activityA onPause")
    }

    override fun onStop() {
        super.onStop()
        println("activityA onStop")
    }
}