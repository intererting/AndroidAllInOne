package com.lqd.commonimp.extend

import android.content.Context
import com.lqd.commonimp.client.tryWithResource
import okio.buffer
import okio.sink
import okio.source
import java.io.File
import java.io.InputStream

/**
 * 获取文件夹大小
 */
fun File.getDirSize(): Double {
    if (!this.exists()) {
        return 0.0
    }
    var totalSize = 0.0
    val fileTree = this.walk()
    fileTree.filter { it.isFile }
            .forEach {
                totalSize += it.length()
            }
    return totalSize
}

/**
 * 删除文件夹
 */
fun File.deleteFile() {
    if (!this.exists()) {
        return
    }
    val fileTree = this.walk()
    fileTree.filter { it.isFile }
            .forEach {
                delete()
            }
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