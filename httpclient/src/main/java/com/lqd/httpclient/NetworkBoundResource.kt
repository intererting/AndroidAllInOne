/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.lqd.httpclient

import androidx.lifecycle.LiveData
import com.lqd.commonimp.util.AbsentLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


abstract class NetworkBoundResource<ResultType> constructor(loadingMsg: String? = null) : BaseNetworkBoundResource<ResultType, ResultType>() {

    init {
        @Suppress("LeakingThis")
        setValue(Resource.loading(null, loadingMsg))
        fetchFromNetwork(AbsentLiveData.create())
    }

    final override fun fetchFromNetwork(dbSource: LiveData<ResultType>) {
        val apiResponse = createCall()
        result.addSource(apiResponse) { response ->
            result.removeSource(apiResponse)
            when (response) {
                is ApiSuccessResponse -> {
                    val responseBody: ResponseEntity<ResultType> = processResponse(response)
                    if (responseBody.responseSuccess) {
                        setValue(Resource.success(responseBody.data, responseBody.resultMsg))
                    } else {
                        onFetchFailed(null, responseBody.resultMsg)
                    }
                }
                is ApiEmptyResponse, is ApiErrorResponse -> {
                    GlobalScope.launch(Dispatchers.Main) {
                        onNetWorkFailed(null)
                    }
                }
            }
        }
    }

    override fun saveCallResult(item: ResultType?) {
    }

    override fun loadFromDb(): LiveData<ResultType> = AbsentLiveData.create()
}
