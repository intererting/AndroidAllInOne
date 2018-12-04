package com.lqd.httpclient

data class ResponseEntity<T>(var resultCode: String = NET_INFO_FAILED,
                             var resultMsg: String? = "",
                             var resultCount: String = "0",
                             val data: T? = null) {
    val responseSuccess: Boolean
        get() = resultCode == NET_INFO_SUCCESS
}