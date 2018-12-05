package com.lqd.httpclient

import androidx.annotation.MainThread
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.lqd.commonimp.client.BaseApplication
import com.lqd.commonimp.extend.showImgToast
import org.jetbrains.anko.toast

@Suppress("LeakingThis")
abstract class BaseNetworkBoundResource<ResultType, RequestType> @MainThread constructor() {

    protected val result = MediatorLiveData<Resource<RequestType>>()

    @MainThread
    protected open fun setValue(newValue: Resource<RequestType>) {
        if (result.value != newValue) {
            result.postValue(newValue)
        }
    }

    protected open fun onFetchFailed(errorMsg: String?, errorCode: Int) {
        errorMsg?.let {
            BaseApplication.provideInstance().toast(it)
        }
        setValue(Resource.failed(null, errorMsg, errorCode))
    }

    protected open fun onNetWorkFailed(data: RequestType?, errorMsg: String?, errorCode: Int) {
        BaseApplication.provideInstance().showImgToast(R.drawable.network_anomaly)
        setValue(Resource.failed(data, errorMsg, errorCode))
    }

    protected abstract fun fetchFromNetwork(dbSource: LiveData<RequestType>)

    open fun asLiveData() = result as LiveData<Resource<RequestType>>

    protected open fun processResponse(response: ApiSuccessResponse<ResponseEntity<ResultType>>): ResponseEntity<ResultType> = response.body

    @WorkerThread
    protected abstract fun saveCallResult(item: ResultType?)

    @WorkerThread
    protected abstract fun loadFromDb(): LiveData<RequestType>

    protected abstract fun createCall(): LiveData<ApiResponse<ResponseEntity<ResultType>>>

}