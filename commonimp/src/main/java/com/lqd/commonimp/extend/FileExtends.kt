package com.lqd.commonimp.extend

import java.io.File

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