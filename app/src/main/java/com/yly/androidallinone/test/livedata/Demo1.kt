package com.yly.androidallinone.test.livedata

import androidx.lifecycle.LiveData

//自定义LiveData

class Demo1 : LiveData<String>() {

    override fun onActive() {
        super.onActive()
    }

}