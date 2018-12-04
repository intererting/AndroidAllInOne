package com.lqd.httpclient

import android.util.Log
import com.lqd.commonimp.client.BaseApplication
import com.lqd.commonimp.extend.provideNetCache
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.net.URLDecoder
import java.util.concurrent.TimeUnit

object RetrofitFactory {

    private var baseUrl: String = ""
    private var enableLog: Boolean = true

    @JvmStatic
    fun init(baseUrl: String, enableLog: Boolean) {
        this.baseUrl = baseUrl
        this.enableLog = enableLog
    }

    val retrofit: Retrofit by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {

        if (baseUrl.isBlank()) {
            throw IllegalArgumentException("RetrofitFactory->baseUrl不能为空")
        }

        val loggingInterceptor = HttpLoggingInterceptor { message ->
            try {
                if (enableLog) {
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

        return@lazy Retrofit.Builder()//
                .baseUrl(baseUrl)//
                .addConverterFactory(MyGsonConverterFactory.create())//
                .addCallAdapterFactory(LiveDataCallAdapterFactory())//
                .client(okhttpClient.build())//
                .build()
    }

    /**
     * 生成对应的Service实例
     */
    @JvmStatic
    fun <T> createService(clazz: Class<T>): T {
        return retrofit.create(clazz)
    }

}