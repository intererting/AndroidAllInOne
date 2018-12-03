package com.lqd.httpclient

import okhttp3.Interceptor
import okhttp3.Response

/**
 * Header拦截器
 */
class HeaderInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val url = originalRequest.url().toString();
        val requestBuilder = originalRequest.newBuilder()//
                .method(originalRequest.method(), originalRequest.body())//
                .url(url)
        return chain.proceed(requestBuilder.build())
    }
}