package com.yuliyang.factorymodule.ws

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TestViewModel : ViewModel() {
    fun test(id: Int) {
        testService.test(id).observeForever {
            resultLD.postValue(it)
        }
    }

    val resultLD = MutableLiveData<String>()

    private val testService by lazy {
        RetrofitServiceImpl()
    }
}