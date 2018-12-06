package com.yly.androidallinone.view.paging

class TestRepository : BaseListRepository() {

    fun test(): Listing<String> {
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