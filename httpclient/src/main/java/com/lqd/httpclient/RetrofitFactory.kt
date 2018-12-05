package com.lqd.httpclient

import android.util.Log
import com.lqd.commonimp.client.BaseApplication
import com.lqd.commonimp.extend.provideNetCache
import com.lqd.httpclient.BuildConfig.BASE_URL
import com.lqd.httpclient.BuildConfig.SHOW_HTTP_LOG
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.net.URLDecoder
import java.util.concurrent.TimeUnit

object RetrofitFactory {

    private val retrofit: Retrofit by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {

        if (BASE_URL.isBlank()) {
            throw IllegalArgumentException("RetrofitFactory->baseUrl不能为空")
        }

        val loggingInterceptor = HttpLoggingInterceptor { message ->
            try {
                if (SHOW_HTTP_LOG) {
                    Log.w("okhttp", URLDecoder.decode(message, "utf-8"))
                }
            } catch (e: Throwable) {
                e.printStackTrace()
            }
        }
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        val okhttpClient = OkHttpClient.Builder()//
                .addInterceptor(loggingInterceptor)//
                .addInterceptor(HeaderInterceptor())
                .connectTimeout(5, TimeUnit.SECONDS)//
                .readTimeout(30, TimeUnit.SECONDS)//
                .writeTimeout(30, TimeUnit.SECONDS)

        val netCache = BaseApplication.provideInstance().provideNetCache()

        if (null != netCache) {
            okhttpClient.cache(Cache(netCache, 5L * 1000 * 1000))
        }

        Retrofit.Builder()//
                .baseUrl(BASE_URL)//
                .addConverterFactory(MyGsonConverterFactory.create())//
                .addCallAdapterFactory(LiveDataCallAdapterFactory())//
                .client(okhttpClient.build())//
                .build()
    }

    /**
     * 生成对应的Service实例
     */
    fun <T> createService(clazz: Class<T>): T {
        return retrofit.create(clazz)
    }

}