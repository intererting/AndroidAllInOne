package com.yly.androidallinone.view

import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.yly.androidallinone.R
import dalvik.system.DexClassLoader
import kotlinx.android.synthetic.main.activity_main.*


class SkinActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        test.setOnClickListener {
//            dynamicLoadApk("${cacheDir.absolutePath}/skin.apk",
//                    getUninstallApkPkgName(this, "${cacheDir.absolutePath}/skin.apk"))
//        }
    }

    private fun dynamicLoadApk(pApkFilePath: String, pApkPacketName: String) {
        val file = getDir("dex", Context.MODE_PRIVATE)
        //第一个参数：是dex压缩文件的路径
        //第二个参数：是dex解压缩后存放的目录
        //第三个参数：是C/C++依赖的本地库文件目录,可以为null
        //第四个参数：是上一级的类加载器
        val classLoader = DexClassLoader(pApkFilePath, file.getAbsolutePath(), null, getClassLoader())
        try {
            val loadClazz = classLoader.loadClass("$pApkPacketName.R\$drawable")
            //插件中皮肤的名称是skin_one
            val skinOneField = loadClazz.getDeclaredField("test")
            skinOneField.isAccessible = true
            //反射获取skin_one的resousreId
            val resousreId = skinOneField.get(R.id::class.java) as Int
            //可以加载插件资源的Resources
            val mContext = createPackageContext(getUninstallApkPkgName(this, "${cacheDir.absolutePath}/skin.apk")
                    , Context.CONTEXT_INCLUDE_CODE or Context.CONTEXT_IGNORE_SECURITY)
            val drawable = mContext.resources.getDrawable(resousreId)
//            test.setImageDrawable(drawable)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }
}

private fun getUninstallApkPkgName(context: Context, pApkFilePath: String): String {
    val pm = context.packageManager
    val pkgInfo = pm.getPackageArchiveInfo(pApkFilePath, PackageManager.GET_ACTIVITIES)
    if (pkgInfo != null) {
        val appInfo = pkgInfo.applicationInfo
        return appInfo.packageName
    }
    return ""
}



