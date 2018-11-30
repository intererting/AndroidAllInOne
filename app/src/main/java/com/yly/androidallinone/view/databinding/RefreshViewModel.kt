package com.yly.androidallinone.view.databinding

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class RefreshViewModel : ViewModel() {
    val refreshStateLD = MutableLiveData<RefreshStateModel>()

    fun testRefresh() {
        refreshStateLD.value = RefreshStateModel(true, "success")
    }

}