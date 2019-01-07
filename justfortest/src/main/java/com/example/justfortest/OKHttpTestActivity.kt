package com.example.justfortest

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_okhttp_test.*
import okhttp3.*
import okio.Okio
import okio.Source
import java.io.IOException

class OKHttpTestActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_okhttp_test)
        testOKHttp.setOnClickListener {
            Thread(Runnable {
                val request = Request.Builder().url("https://www.baidu.com").build()
                OkHttpClient().newCall(request).enqueue(object : Callback {
                    override fun onFailure(call: Call, e: IOException) {
                    }

                    override fun onResponse(call: Call, response: Response) {
                        println(response.body()?.string())
                    }

                })
            }).start()
        }
    }
}