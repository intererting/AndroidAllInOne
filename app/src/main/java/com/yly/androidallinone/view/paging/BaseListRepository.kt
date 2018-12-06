package com.yly.androidallinone.view.paging

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.paging.DataSource
import androidx.paging.PageKeyedDataSource
import androidx.paging.toLiveData

abstract class BaseListRepository {

    protected val networkState = MutableLiveData<NetworkState>()

    open var retry: (() -> Any)? = null

    private val sourceFactory by lazy {
        return@lazy object : DataSource.Factory<Int, String>() {
            override fun create(): DataSource<Int, String> {
                return wrappersSource
            }
        }
    }

    private val wrappersSource =
            object : PageKeyedDataSource<Int, String>() {

                fun retryAllFailed() {
                    val prevRetry = retry
                    retry = null
                    prevRetry?.invoke()
                }

                override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, String>) {
                    fetchData(0) {
                        callback.onResult(it, null, 1)
                    }
                }

                override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, String>) {
                    var pageNo = params.key
                    fetchData(pageNo) {
                        callback.onResult(it, ++pageNo)
                    }
                }

                override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, String>) {
                }

            }

    val baseListing by lazy {
        return@lazy Listing(
                pagedList = sourceFactory.toLiveData(pageSize = 20, initialLoadKey = 0),
                networkState = Transformations.map(networkState) {
                    it
                },
                retry = {
                    wrappersSource.retryAllFailed()
                },
                refresh = {
                    wrappersSource.invalidate()
                }
        )
    }

    abstract fun fetchData(pageNo: Int, callback: (List<String>) -> Unit)
}


