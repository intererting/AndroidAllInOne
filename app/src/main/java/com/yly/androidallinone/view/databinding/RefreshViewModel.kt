package com.yly.androidallinone.view.databinding

import androidx.lifecycle.MutableLiveData
import com.lqd.commonimp.client.BaseViewModel

class RefreshViewModel : BaseViewModel() {
    val refreshStateLD = MutableLiveData<RefreshStateModel>()

    //    //列表数据
//    val datasLD = MutableLiveData<List<String>>()
//
    val listInfoModelLD = MutableLiveData<ListInfoModel<String>>()

    fun testRefresh(pageNo: Int) {
        val datas = arrayListOf<String>()
        for (i in 0..30) {
            datas.add(i.toString())
        }
        listInfoModelLD.value = ListInfoModel(pageNo == 0, datas)
        refreshStateLD.value = RefreshStateModel(true, "success")
    }

}