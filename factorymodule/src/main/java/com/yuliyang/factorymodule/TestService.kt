package com.yuliyang.factorymodule

import android.app.*
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import java.net.Socket
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

class TestService : Service() {
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate() {
        super.onCreate()
        val builder = NotificationCompat.Builder(this, createChannel())
        builder.setTicker("socket运行中")
        builder.setContentInfo("ContentInfo")
        startForeground(1, builder.build())

        Thread {
            val timerTask = Executors.newScheduledThreadPool(1)
            val clientSocket = Socket("192.168.2.49", 12346)
            val outputStream = clientSocket.getOutputStream()
            timerTask.scheduleAtFixedRate({
                outputStream.write("fromAndroid".toByteArray(charset("utf-8")))
            }, 0, 1000, TimeUnit.MILLISECONDS)
        }.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        startService(Intent(this, TestService::class.java))
    }

}

/**
 * 获取通知渠道
 */
@RequiresApi(Build.VERSION_CODES.O)
fun Context.createChannel(channelId: String = "default_channel"
                          , channelName: String = "default_name"
                          , importance: Int = NotificationManager.IMPORTANCE_DEFAULT): String {
    val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
    val notificationChannel: NotificationChannel? = notificationManager.getNotificationChannel(channelId)
    if (null == notificationChannel) {
        //创建channel
        val newChannel = NotificationChannel(channelId, channelName, importance)
        notificationManager.createNotificationChannel(newChannel);
    }
    return channelId
}

/**
 * 显示一个Notification
 */
fun Context.showNotification(notifiId: Int
                             , notifyTitle: String
                             , notifyContent: String
                             , channelId: String = "default_channel"
                             , channelName: String = "default_name"
                             , importance: Int = NotificationManager.IMPORTANCE_DEFAULT) {
    val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
    val builder: NotificationCompat.Builder
    if (isVersion8OrAbove()) {
        builder = NotificationCompat.Builder(this, createChannel(channelId, channelName, importance))
    } else {
        builder = NotificationCompat.Builder(this)
    }
    val notification = builder.setContentTitle(notifyTitle)
            .setContentText(notifyContent)
            .setWhen(System.currentTimeMillis())
            .setSmallIcon(R.mipmap.ic_launcher)
            .setAutoCancel(true)
            .build();
    notificationManager.notify(notifiId, notification);
}

fun Context.cancelNotification(notifiId: Int) {
    val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
    notificationManager.cancel(notifiId)
}

/**
 * 是否8.0以上
 */
fun Context.isVersion8OrAbove() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.O