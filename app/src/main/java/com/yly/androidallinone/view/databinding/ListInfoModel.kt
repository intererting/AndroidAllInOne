package com.yly.androidallinone.view.databinding

import androidx.lifecycle.MutableLiveData

class ListInfoModel<DataType>() {

    //页数
    val pageInfoLD = PageInfo()

    val datasLD = MutableLiveData<List<DataType>>()

    val isRefreshLD = MutableLiveData<Boolean>()

    constructor(isRefresh: Boolean, datas: List<DataType>) : this() {
        datasLD.value = datas
        isRefreshLD.value = isRefresh
    }
}