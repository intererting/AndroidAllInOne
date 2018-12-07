package com.yly.androidallinone.view.paging

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import org.jetbrains.anko.doAsync
import java.io.ByteArrayOutputStream
import java.io.OutputStream

class TestRepository : BaseListRepository() {

    fun test(): Listing<String> {
//        GlobalScope.launch {
//            async {
//                testA()
//            }.await()
//        }

        return baseListing
    }


    override fun fetchData(pageNo: Int, callback: (List<String>) -> Unit) {
        if (pageNo < 3) {
            val list = mutableListOf<String>()
            for (i in pageNo * 20 until (pageNo + 1) * 20) {
                list.add(i.toString())
            }
            callback(list)
        }
        networkState.postValue(NetworkState.COMPLETE)
    }
}