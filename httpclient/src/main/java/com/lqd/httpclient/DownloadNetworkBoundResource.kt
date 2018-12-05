package com.lqd.httpclient

import androidx.lifecycle.LiveData
import com.lqd.commonimp.util.AbsentLiveData
import okhttp3.ResponseBody

/**
 * 文件下载
 */
abstract class DownloadNetworkBoundResource(loadingMsg: String = "正在下载") : BaseNetworkBoundResource<ResponseBody, ResponseBody>() {

    init {
        @Suppress("LeakingThis")
        setValue(Resource.loading(null, loadingMsg))
        fetchFromNetwork(AbsentLiveData.create())
    }

    final override fun fetchFromNetwork(dbSource: LiveData<ResponseBody>) {
        val apiResponse = createDownloadCall()
        result.addSource(apiResponse) { response ->
            result.removeSource(apiResponse)
            when (response) {
                is ApiSuccessResponse -> {
                    setValue(Resource.success(response.body, ""))
                }
                is ApiErrorResponse -> {
                    setValue(Resource.failed(null, "下载失败", response.errorCode))
                }
            }
        }
    }

    override fun saveCallResult(item: ResponseBody?) {
    }

    override fun createCall(): LiveData<ApiResponse<ResponseEntity<ResponseBody>>> {
        return AbsentLiveData.create()
    }

    override fun loadFromDb(): LiveData<ResponseBody> = AbsentLiveData.create()

    protected abstract fun createDownloadCall(): LiveData<ApiResponse<ResponseBody>>

}