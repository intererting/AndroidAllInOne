package com.yly.androidallinone.view.paging

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.lqd.commonimp.client.BaseViewModel

class PagingViewModel : BaseViewModel() {

    val repoResult = MutableLiveData<Listing<String>>()

    private val repository by lazy {
        TestRepository()
    }

    val dataList = Transformations.switchMap(repoResult) {
        it.pagedList
    }

    val refreshState = Transformations.switchMap(repoResult) {
        it.networkState
    }

    fun refresh() {
        repoResult.value?.refresh?.invoke()
    }


    fun testPaging() {
        repoResult.value = repository.test()
    }
//        val sourceFactory = MyDataSourceFactory()
//
//        // We use toLiveData Kotlin extension function here, you could also use LivePagedListBuilder
////    val livePagedList =
//        return sourceFactory.toLiveData(
//                config = Config(pageSize = 20, prefetchDistance = 10))
//    }
}