package com.lqd.commonimp.extend

import android.app.ActivityManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.ContentResolver
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.RoundRectShape
import android.location.LocationManager
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.os.Process
import android.provider.MediaStore
import android.text.TextUtils
import android.util.DisplayMetrics
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.*
import androidx.annotation.DrawableRes
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.FileProvider
import androidx.loader.content.CursorLoader
import com.lqd.commonimp.BuildConfig
import com.lqd.commonimp.R
import com.lqd.commonimp.client.NOTIFICATION_DEFAULT_CHANNEL_ID
import com.lqd.commonimp.client.NOTIFICATION_DEFAULT_CHANNEL_NAME
import com.lqd.commonimp.client.tryWithResource
import okio.buffer
import okio.sink
import okio.source
import org.jetbrains.anko.defaultSharedPreferences
import org.jetbrains.anko.dip
import java.io.*

fun Context.showImgToast(@DrawableRes resId: Int) {
    val imageView = ImageView(this)
    // 设置尺寸
    val imageLength = dip(120)
    val mParams = ViewGroup.LayoutParams(imageLength, imageLength)
    imageView.layoutParams = mParams
    imageView.setBackgroundResource(resId)
    // new一个toast传入要显示的activity的上下文
    val mToast = Toast(this)
    // 显示的时间
    mToast.duration = Toast.LENGTH_SHORT
    // 显示的位置
    mToast.setGravity(Gravity.CENTER, 0, 0)
    // 重新给toast进行布局
    val toastLayout = RelativeLayout(this)
    // 把imageView添加到toastLayout的布局当中
    toastLayout.addView(imageView)
    // 把toastLayout添加到toast的布局当中
    mToast.view = toastLayout
    mToast.apply { show() }
}

fun Context.centerToast(msg: CharSequence, duration: Int = Toast.LENGTH_SHORT) {
    val mToast = Toast.makeText(this, msg, duration)
    try {
        val rectShape = RoundRectShape(floatArrayOf(100f, 100f, 100f, 100f, 100f, 100f, 100f, 100f), null, null)
        val shapeDrawable = ShapeDrawable(rectShape).apply {
            paint.color = Color.argb(210, 255, 255, 255)
            paint.style = Paint.Style.FILL
            paint.isAntiAlias = true
            paint.flags = Paint.ANTI_ALIAS_FLAG
        }
        mToast.view.background = shapeDrawable
        mToast.view.leftPadding = dip(16)
        mToast.view.rightPadding = dip(16)
        val text = mToast.view.findViewById<TextView>(android.R.id.message)
        text.setTextColor(Color.BLACK)
    } catch (e: Exception) {
        e.printStackTrace()
    }
    mToast.setGravity(Gravity.CENTER, 0, 0)
    mToast.show()
}

fun Context.tips(msg: CharSequence) {
    centerToast(msg)
}

fun Context.emptyView(@DrawableRes imgRes: Int, msg: CharSequence = ""): View {
    val linearLayout = LinearLayout(this).apply {
        layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        gravity = Gravity.CENTER
        orientation = LinearLayout.VERTICAL
    }
    val imageView = ImageView(this).apply {
        layoutParams = LinearLayout.LayoutParams(dip(260), dip(260))
        setImageResource(imgRes)
    }
    val textView = TextView(this).apply {
        layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        setTextColor(R.color.textColor_gray.getColor())
        text = msg
    }
    linearLayout.addView(imageView)
    linearLayout.addView(textView)
    return linearLayout
}

/**
 * 检查手机上是否安装了指定的软件
 */
fun Context.isAppInstalled(packageName: String): Boolean {
    //获取packagemanager
    val packageManager = packageManager
    //获取所有已安装程序的包信息
    val packageInfos = packageManager.getInstalledPackages(0)
    //从pinfo中将包名字逐一取出，压入pName list中
    if (packageInfos != null) {
        for (packageInfo in packageInfos) {
            if (packageInfo.packageName == packageName) {
                return true
            }
        }
    }
    return false
}

fun Context.loadImageDefault(width: Int, height: Int): BitmapDrawable {
    var realWidth = width
    var realHeight = height
    if (realHeight == 0) {
        realHeight = screenWidth
    }
    if (realWidth == 0) {
        realWidth = screenWidth
    }
    val defaultImg = R.drawable.load_image_default.getDrawable() as BitmapDrawable
    val drawableWidth = defaultImg.intrinsicWidth
    val drawableHeight = defaultImg.intrinsicHeight
    val bitmap = Bitmap.createBitmap(realWidth, realHeight, Bitmap.Config.RGB_565);
    val canvas = Canvas(bitmap);
    canvas.drawColor(R.color.app_bg.getColor())
    canvas.drawBitmap(defaultImg.bitmap, (realWidth / 2 - drawableWidth / 2).toFloat(), (realHeight / 2.0 - drawableHeight / 2).toFloat(), Paint())
    return BitmapDrawable(resources, bitmap)
}

/**
 * SP存储
 */
inline fun Context.spApply(spName: String? = null, modifier: SharedPreferences.Editor.() -> Unit) {
    val editor = getSharedPreferences(spName).edit()
    editor.modifier()
    editor.apply()
}

/**
 * 获取网络缓存路径
 */
fun Context.provideNetCache(): File? {
    val cacheDir = cacheDir
    cacheDir?.apply {
        if (!this.exists()) {
            this.mkdirs()
        }
        val result = File(this, "netCache")
        return if (!result.mkdirs() && (!result.exists() || !result.isDirectory)) null else result
    }
    return null
}

/**
 * 外部存储文件缓存路径
 */
@Throws(RuntimeException::class)
fun Context.provideExternalFileCache(): File? {
    if (!isSDCardEnable) {
        throw RuntimeException("存储卡异常")
    }
    externalCacheDir?.apply {
        if (!this.exists()) {
            this.mkdirs()
        }
        val result = File(this, "fileCache")
        return if (!result.mkdirs() && (!result.exists() || !result.isDirectory)) null else result
    }
    return null
}

/**
 * 文件缓存路径
 */
fun Context.provideFileCache(): File? {
    cacheDir?.apply {
        if (!this.exists()) {
            this.mkdirs()
        }
        val result = File(this, "fileCache")
        return if (!result.mkdirs() && (!result.exists() || !result.isDirectory)) null else result
    }
    return null
}

/**
 * 将文件写入本地
 * @param destFile 写入地址
 * @param resourceIs 原文件流
 */
fun Context.writeFileToDisk(destFile: File, resourceIs: InputStream): File? {
    return tryWithResource {
        if (!destFile.exists()) {
            destFile.createNewFile()
        }
        val bSink = destFile.sink().buffer().autoClose()
        val bSource = resourceIs.source().buffer().autoClose()
        bSink.writeAll(bSource)
        destFile
    }
}


/**
 * 获取通知渠道
 */
@RequiresApi(Build.VERSION_CODES.O)
fun Context.createChannel(channelId: String = NOTIFICATION_DEFAULT_CHANNEL_ID
                          , channelName: String = NOTIFICATION_DEFAULT_CHANNEL_NAME
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
                             , notifySmallIcon: Int
                             , channelId: String = NOTIFICATION_DEFAULT_CHANNEL_ID
                             , channelName: String = NOTIFICATION_DEFAULT_CHANNEL_NAME
                             , importance: Int = NotificationManager.IMPORTANCE_DEFAULT) {
    val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
    val builder: NotificationCompat.Builder
    if (isVersion8Above()) {
        builder = NotificationCompat.Builder(this, createChannel(channelId, channelName, importance))
    } else {
        builder = NotificationCompat.Builder(this)
    }
    val notification = builder.setContentTitle(notifyTitle)
            .setContentText(notifyContent)
            .setWhen(System.currentTimeMillis())
            .setSmallIcon(notifySmallIcon)
            .setAutoCancel(true)
            .build();
    notificationManager.notify(notifiId, notification);
}

fun Context.cancelNotification(notifiId: Int) {
    val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
    notificationManager.cancel(notifiId)
}

fun Context.log(info: String) {
    if (BuildConfig.FLAVOR == "mock") {
        Log.e(javaClass.simpleName, info)
    }
}

/**
 * 安装APK
 */
fun Context.installAPK(file: File, authority: String) {
    val intent = Intent(Intent.ACTION_VIEW)
    if (isVersion7Above()) {
        val apkUri = FileProvider.getUriForFile(this, authority, file)
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.setDataAndType(apkUri, "application/vnd.android.package-archive")
    } else {
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive")
    }
    intent.addCategory("android.intent.category.DEFAULT")
    intent.action = "android.intent.action.VIEW"
    startActivity(intent)
    System.exit(0)
}


/**
 * 是否6.0以上
 */
fun Context.isVersion6Above() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.M

/**
 * 是否7.0以上
 */
fun Context.isVersion7Above() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.N

/**
 * 是否8.0以上
 */
fun Context.isVersion8Above() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.O

/**
 * 是否5.0以上
 */
fun Context.isVersion5Above() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP

///*********************************扩展属性*****************************///

inline val Context.isSDCardEnable: Boolean
    get() = Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED

/**
 * 获取状态栏的高度
 */
inline val Context.statusBarHeight: Int
    get() {
        var result = 0
        val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            result = resources.getDimensionPixelSize(resourceId)
        }
        return result
    }

/**
 * 获取屏幕宽度
 */
inline val Context.screenWidth: Int
    get() {
        val wm: WindowManager? = getSystemService(Context.WINDOW_SERVICE) as? WindowManager
        wm?.apply {
            val metrics = DisplayMetrics()
            wm.defaultDisplay.getMetrics(metrics)
            return metrics.widthPixels
        }
        return 0
    }


/**
 * 获取屏幕高度
 */
inline val Context.screenHeight: Int
    get() {
        val wm: WindowManager? = getSystemService(Context.WINDOW_SERVICE) as? WindowManager
        wm?.apply {
            val point = Point()
            wm.defaultDisplay.getRealSize(point)
            return point.y
        }
        return 0
    }

/**
 * 获取屏幕高度(包括底部导航栏)
 */
inline val Context.screenRealHeight: Int
    get() {
        val wm: WindowManager? = getSystemService(Context.WINDOW_SERVICE) as? WindowManager
        wm?.apply {
            val metrics = DisplayMetrics()
            wm.defaultDisplay.getMetrics(metrics)
            return metrics.heightPixels
        }
        return 0
    }


/**
 * appName
 */
inline val Context.appName: String
    get() {
        try {
            val packageInfo = packageManager.getPackageInfo(packageName, 0)
            val labelRes = packageInfo.applicationInfo.labelRes
            return resources.getString(labelRes)
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
        return ""
    }

/**
 * versionName
 */
inline val Context.versionName: String
    get() {
        try {
            val packageInfo = packageManager.getPackageInfo(packageName, 0)
            return packageInfo.versionName
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
        return ""
    }

/**
 * processName
 */
inline val Context.processName: String
    get() {
        var reader: BufferedReader? = null
        try {
            reader = BufferedReader(FileReader("/proc/" + Process.myPid() + "/cmdline"))
            var processName = reader.readLine()
            if (!TextUtils.isEmpty(processName)) {
                processName = processName.trim { it <= ' ' }
            }
            return processName
        } catch (throwable: Throwable) {
            throwable.printStackTrace()
        } finally {
            try {
                reader?.close()
            } catch (exception: IOException) {
                exception.printStackTrace()
            }
        }
        return ""
    }

/**
 * app是否存活
 */
inline val Context.isAppAlive: Boolean
    get() {
        val activityManager: ActivityManager? = getSystemService(Context.ACTIVITY_SERVICE) as? ActivityManager
        activityManager?.apply {
            for (i in runningAppProcesses.indices) {
                if (runningAppProcesses[i].processName == packageName) {
                    return true
                }
            }
        }
        return false
    }

/**
 * 当前Activity是否在栈顶
 */
inline val Context.isTopActivity: Boolean
    get() {
        val activityManager: ActivityManager? = getSystemService(Context.ACTIVITY_SERVICE) as? ActivityManager
        activityManager?.apply {
            val tasksInfo = getRunningTasks(1)
            return tasksInfo.size > 0 && packageName == tasksInfo[0].topActivity.packageName
        }
        return false
    }

/**
 * GPS是否打开
 */
inline val Context.isGpsOpen: Boolean
    get() {
        var isOpen: Boolean = false
        val locationManager: LocationManager? = getSystemService(Context.LOCATION_SERVICE) as? LocationManager
        locationManager?.apply {
            isOpen = isProviderEnabled(LocationManager.GPS_PROVIDER)
        }
        return isOpen
    }


/**
 * 通知是否可用
 */
inline val Context.isNotificationEnable: Boolean
    get() = NotificationManagerCompat.from(this).areNotificationsEnabled()

/**
 * 获取SharedPreferences
 */
fun Context.getSharedPreferences(spName: String? = null): SharedPreferences {
    return if (spName.isNullOrBlank()) defaultSharedPreferences else
        getSharedPreferences(spName, Context.MODE_PRIVATE)
}


fun Context.getAssetsFile(fileName: String): String {
    try {
        return this.assets.open(fileName).reader().readText()
    } catch (e: IOException) {
        e.printStackTrace()
        return ""
    }
}


fun Context.file2Uri(file: File): Uri {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        FileProvider.getUriForFile(this, "com.readygo.yf.fileprovider", file)
    } else {
        Uri.fromFile(file)
    }
}

fun Context.uri2File(uri: Uri): File {
    if (ContentResolver.SCHEME_FILE == uri.scheme) {
        return File(uri.path)
    }
    val cl = CursorLoader(this)
    cl.uri = uri
    cl.projection = arrayOf(MediaStore.Video.Media.DATA)
    var cursor: Cursor? = null
    try {
        cursor = cl.loadInBackground()
        val columnIndex = cursor!!.getColumnIndexOrThrow(MediaStore.Video.Media.DATA)
        cursor.moveToFirst()
        return File(cursor.getString(columnIndex))
    } finally {
        cursor?.close()
    }
}