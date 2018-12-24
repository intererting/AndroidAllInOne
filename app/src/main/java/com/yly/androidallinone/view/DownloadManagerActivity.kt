package com.yly.androidallinone.view

import android.Manifest
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.yly.androidallinone.R
import kotlinx.android.synthetic.main.activity_download.*
import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import android.os.Build
import android.webkit.MimeTypeMap
import androidx.annotation.RequiresApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.File
import android.app.DownloadManager.Request.NETWORK_WIFI
import android.app.DownloadManager.Request.NETWORK_MOBILE
import android.os.Environment


class DownloadManagerActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestPermissions(arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 10)
        setContentView(R.layout.activity_download)
        val intentFilter = IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE)

        registerReceiver(object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                intent?.apply {
                    when (intent.action) {
                        DownloadManager.ACTION_DOWNLOAD_COMPLETE -> {
                            println(intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, 0))
                            println("下载完成")
                        }
                    }
                }
            }
        }, intentFilter)

        start.setOnClickListener {
            GlobalScope.launch {
                val downloadManager = getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
                val apkUrl = "http://d1.music.126.net/dmusic/CloudMusic_official_5.7.2.122043.apk"
                val request = DownloadManager.Request(Uri.parse(apkUrl))
//                println(Uri.withAppendedPath(Uri.fromFile(cacheDir), "test.jpg").toString())
                request.setDestinationUri(Uri.withAppendedPath(Uri.fromFile(externalCacheDir), "test.apk"))
                request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE or DownloadManager.Request.NETWORK_WIFI)
//                request.setDestinationInExternalFilesDir(this@DownloadManagerActivity, Environment.DIRECTORY_DOWNLOADS, "test.apk")
                request.setTitle("MeiLiShuo")//设置下载中通知栏提示的标题
                request.setDescription("MeiLiShuo desc")//设置下载中通知栏提示的介绍
                println(downloadManager.enqueue(request))
            }
        }
    }
}