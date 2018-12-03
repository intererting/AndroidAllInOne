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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


abstract class NetworkBoundResourceWithDb<ResultType, RequestType> constructor(val loadingMsg: String? = null) : BaseNetworkBoundResource<ResultType, RequestType>() {

    init {
        GlobalScope.launch(Dispatchers.IO) {
            val dbSource = loadFromDb()
            fetchFromNetwork(dbSource)
        }
    }

    final override fun fetchFromNetwork(dbSource: LiveData<RequestType>) {
        result.addSource(dbSource) {
            setValue(Resource.loading(it, loadingMsg))
        }
        val apiResponse = createCall()
        result.addSource(apiResponse) { response ->
            result.removeSource(apiResponse)
            result.removeSource(dbSource)
            when (response) {
                is ApiSuccessResponse -> {
                    val responseBody: ResponseEntity<ResultType> = processResponse(response)
                    if (responseBody.responseSuccess) {
                        GlobalScope.launch(Dispatchers.IO) {
                            saveCallResult(responseBody.data)
                            val loadDb = loadFromDb()
                            result.addSource(loadDb) {
                                result.removeSource(loadDb)
                                setValue(Resource.success(it, responseBody.resultMsg))
                            }
                        }
                    } else {
//                        val loadDb = loadFromDb()
//                        result.addSource(loadDb) {
//                            result.removeSource(loadDb)
//                            onFetchFailed(it, responseBody.resultMsg)
//                        }
                        onFetchFailed(null, responseBody.resultMsg)
                    }
                }
                is ApiEmptyResponse, is ApiErrorResponse -> {
                    val loadDb = loadFromDb()
                    result.addSource(loadDb) {
                        result.removeSource(loadDb)
                        onNetWorkFailed(it)
                    }
                }
            }
        }
    }
}
