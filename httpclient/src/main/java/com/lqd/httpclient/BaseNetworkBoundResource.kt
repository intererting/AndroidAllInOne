package com.lqd.httpclient

import androidx.annotation.MainThread
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.lqd.commonimp.client.BaseApplication
import com.lqd.commonimp.model.ResponseEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.jetbrains.anko.toast

@Suppress("LeakingThis")
abstract class BaseNetworkBoundResource<ResultType, RequestType> @MainThread constructor() {

    protected val result = MediatorLiveData<Resource<RequestType>>()

    @MainThread
    protected open fun setValue(newValue: Resource<RequestType>) {
        GlobalScope.launch(Dispatchers.Main) {
            if (result.value != newValue) {
                result.value = newValue
            }
        }
    }

    protected open fun onFetchFailed(data: RequestType?, errorMsg: String?) {
        errorMsg?.let {
            BaseApplication.provideInstance().toast(it)
        }
        setValue(Resource.failed(data))
    }

    protected open fun onNetWorkFailed(data: RequestType?) {
        //TODO
//        YF.provideInstance().showImgToast(R.drawable.network_anomaly)
        setValue(Resource.failed(data))
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