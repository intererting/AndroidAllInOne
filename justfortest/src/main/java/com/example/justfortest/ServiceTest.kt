package com.example.justfortest

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder

class ServiceTest : Service() {
    override fun onBind(intent: Intent): IBinder {
        println("onBind")
        return MyBinder()
    }

    class MyBinder : Binder() {

    }

    override fun onRebind(intent: Intent?) {
        super.onRebind(intent)
        println("onRebind")
    }

    override fun onCreate() {
        super.onCreate()
        println("onCreate")
    }

    override fun onDestroy() {
        super.onDestroy()
        println("onDestroy")
    }

    override fun onUnbind(intent: Intent?): Boolean {
        println("onUnBind")
        return super.onUnbind(intent)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        println("onStartCommand")
        return super.onStartCommand(intent, flags, startId)
    }
}