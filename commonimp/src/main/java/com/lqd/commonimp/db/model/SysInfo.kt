package com.lqd.commonimp.db.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
        tableName = "t_sysinfo"
)
data class SysInfo(var systemInfoId: String = "",
                   var verNo: String = "",
                   var addUser: String = "",
                   var addTime: String = "",
                   var updUser: String = "",
                   var updTime: String = "",
                   var systemName: String = "",
                   var androidPackageName: String = "",
                   var androidVersion: String = "",
                   var iosPackageName: String = "",
                   var iosVersion: String = "",
                   var androidDownloadUrl: String = "",
                   var iosDownloadUrl: String = "",
                   var resourceUrl: String = "",
                   var aboutUs: String = "") {
    @PrimaryKey(autoGenerate = true)
    var _id: Int = 0
}