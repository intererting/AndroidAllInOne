package com.lqd.commonimp.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.lqd.commonimp.db.model.SysInfo

@Dao
abstract class SysInfoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertSysInfo(sysInfo: SysInfo)

    @Query(
            """
        SELECT * FROM t_sysinfo
       """
    )
    abstract fun loadSysInfo(): LiveData<SysInfo>

    @Query(
            """
                DELETE FROM t_sysinfo WHERE 1==1
            """
    )
    abstract fun deleteAll()
}