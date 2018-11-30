package com.yly.androidallinone.view.databinding

import androidx.lifecycle.MutableLiveData

class RefreshStateModel() {

    val completeLD = MutableLiveData<Boolean>()
    val msgLD = MutableLiveData<String>()

    constructor(complete: Boolean, msg: String) : this() {
        completeLD.value = complete
        msgLD.value = msg
    }
}