package com.yly.androidallinone.view.paging

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.paging.DataSource
import androidx.paging.PageKeyedDataSource
import androidx.paging.toLiveData
import com.lqd.commonimp.client.BaseApplication
import com.lqd.commonimp.extend.log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class PagingRepository {

    private val sourceFactory by lazy {
        MyDataSourceFactory()
    }

    fun testPaging(): Listing<String> {

        return Listing(
                pagedList = sourceFactory.toLiveData(pageSize = 20),
                networkState = Transformations.switchMap(sourceFactory.sourceLiveData) {
                    it.networkState
                },
                retry = {
                    sourceFactory.sourceLiveData.value?.retryAllFailed()
                },
                refresh = {
                    sourceFactory.sourceLiveData.value?.invalidate()
                }
        )
    }


}

class MyDataSourceFactory : DataSource.Factory<Int, String>() {

    val sourceLiveData = MutableLiveData<MyDataSource>()

    override fun create(): DataSource<Int, String> {
        val source = MyDataSource()
        sourceLiveData.postValue(source)
        return source

    }
}

class MyDataSource : PageKeyedDataSource<Int, String>() {

    private var retry: (() -> Any)? = null

    val networkState = MutableLiveData<NetworkState>()

    fun retryAllFailed() {
        val prevRetry = retry
        retry = null
        prevRetry?.let {
            GlobalScope.launch(Dispatchers.IO) {
                it.invoke()
            }
        }
    }

    private fun fetchData(pageNo: Int, callback: (List<String>) -> Unit) {
        if (pageNo < 3) {
            val list = mutableListOf<String>()
            for (i in pageNo * 20 until (pageNo + 1) * 20) {
                list.add(i.toString())
            }
            callback(list)
        }
        networkState.postValue(NetworkState.COMPLETE)
    }

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, String>) {
        BaseApplication.provideInstance().log("loadInitial")

        fetchData(0) {
            callback.onResult(it, null, 1)
        }

    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, String>) {
        BaseApplication.provideInstance().log("params.key  ${params.key}")

        var pageNo = params.key
        fetchData(pageNo) {
            callback.onResult(it, ++pageNo)
        }
//        if (pageNo == 3) {
//            callback.onResult(arrayListOf(), null)
//        } else {
//            callback.onResult(list, (++pageNo).toString())
//        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, String>) {
        BaseApplication.provideInstance().log("loadBefore")
    }
}