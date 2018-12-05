package com.lqd.commonimp.db

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.lqd.commonimp.client.BaseApplication
import com.lqd.commonimp.db.dao.SysInfoDao
import com.lqd.commonimp.db.model.SysInfo


@Database(
        entities = [SysInfo::class],
        version = 1,
        exportSchema = false
)
abstract class DatabaseFactory : RoomDatabase() {

    abstract fun sysinfoDao(): SysInfoDao

    companion object {
        val db: DatabaseFactory by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            Room.databaseBuilder(BaseApplication.provideInstance(),
                    DatabaseFactory::class.java, "lqd_database")
                    .build()
        }
    }
}