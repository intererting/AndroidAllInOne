package com.yuliyang.factorymodule.ws

import android.os.SystemClock
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class RetrofitServiceImpl : TestService {
    override fun test(id: Int): LiveData<String> {
        return getLiveData()
    }

    private fun getLiveData(): LiveData<String> {
        val liveData = MutableLiveData<String>()
        Thread {
            println("获取网络数据")
            SystemClock.sleep(2000)
            liveData.postValue("yuliyang")
        }.start()

        return liveData
    }
}