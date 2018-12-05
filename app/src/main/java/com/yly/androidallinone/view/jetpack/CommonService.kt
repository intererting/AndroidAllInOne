package com.yly.androidallinone.view.jetpack

import androidx.lifecycle.LiveData
import com.lqd.commonimp.db.model.SysInfo
import com.lqd.httpclient.ApiResponse
import com.lqd.httpclient.ResponseEntity
import retrofit2.http.POST


interface CommonService {

    @POST("getSystemData")
    fun getSystemData(): LiveData<ApiResponse<ResponseEntity<SysInfo>>>
}