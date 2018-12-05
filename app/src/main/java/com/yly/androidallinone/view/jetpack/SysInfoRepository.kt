package com.yly.androidallinone.view.jetpack

import androidx.lifecycle.LiveData
import com.lqd.commonimp.client.BaseDbRepository
import com.lqd.commonimp.db.model.SysInfo
import com.lqd.httpclient.NetworkBoundResourceWithDb
import com.lqd.httpclient.Resource
import com.lqd.httpclient.RetrofitFactory

class SysInfoRepository : BaseDbRepository() {

    private val sysInfoDao by lazy {
        db.sysinfoDao()
    }

    private val commonService by lazy {
        RetrofitFactory.createService(CommonService::class.java)
    }

    fun getSystemData(): LiveData<Resource<SysInfo>> {
        return object : NetworkBoundResourceWithDb<SysInfo, SysInfo>() {
            override fun saveCallResult(item: SysInfo?) {
                dbWithTransaction {
                    sysInfoDao.deleteAll()
                    item?.let {
                        sysInfoDao.insertSysInfo(it)
                    }
                }
            }

            override fun loadFromDb(): LiveData<SysInfo> {
                return sysInfoDao.loadSysInfo()
            }

            override fun createCall() = commonService.getSystemData()

        }.asLiveData()
    }
}