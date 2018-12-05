package com.yly.androidallinone.view.jetpack

import androidx.lifecycle.LiveData
import com.lqd.commonimp.client.BaseViewModel
import com.lqd.commonimp.db.model.SysInfo
import com.lqd.httpclient.Resource

class SysInfoViewModel : BaseViewModel() {

    private val repository by lazy {
        SysInfoRepository()
    }

    /**
     * 获取系统信息
     */
    fun getSystemData(): LiveData<Resource<SysInfo>> {
        return repository.getSystemData()
    }

}