package com.yly.androidallinone.view

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.paging.PageKeyedDataSource
import androidx.paging.PagedList
import com.lqd.commonimp.client.BaseApplication
import com.lqd.commonimp.client.BaseViewModel
import com.lqd.commonimp.extend.log

class PagingViewModel : BaseViewModel() {

    val datas = MutableLiveData<PagedList<String>>()



}

class MyDataSourceFactory : DataSource.Factory<String, String>() {

    override fun create(): DataSource<String, String> {

        return MyDataSource()

    }
}

class MyDataSource : PageKeyedDataSource<String, String>() {
    var pageNo: Int = 0

    override fun loadInitial(params: LoadInitialParams<String>, callback: LoadInitialCallback<String, String>) {
        BaseApplication.provideInstance().log("loadInitial")
        val list = mutableListOf<String>()
        for (i in 0 until 20) {
            list.add(i.toString())
        }

        callback.onResult(list, "0", (++pageNo).toString())

    }

    override fun loadAfter(params: LoadParams<String>, callback: LoadCallback<String, String>) {
        BaseApplication.provideInstance().log("params.key  ${params.key}")
    }

    override fun loadBefore(params: LoadParams<String>, callback: LoadCallback<String, String>) {
        BaseApplication.provideInstance().log("loadBefore")
    }
}