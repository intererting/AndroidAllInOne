package com.lqd.httpclient

import ClientManager
import android.app.Application
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

object RetrofitFactory {

    lateinit var mContext: Application

    @JvmStatic
    fun init(mContext: Application) {
        this.mContext = mContext
    }

    val retrofit: Retrofit by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
        val loggingInterceptor = ClientManager.loggingInterceptor
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        val okhttpClient = OkHttpClient.Builder()//
                .addInterceptor(loggingInterceptor)//
                .addInterceptor(HeaderInterceptor())
                .connectTimeout(5, TimeUnit.SECONDS)//
                .readTimeout(30, TimeUnit.SECONDS)//
                .writeTimeout(30, TimeUnit.SECONDS)

//        val netCache = mContext.provideNetCache()

//        if (null != netCache) {
//            okhttpClient.cache(Cache(netCache, 5L * 1000 * 1000))
//        }

        return@lazy Retrofit.Builder()//
                .baseUrl(ClientManager.baseUrl)//
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