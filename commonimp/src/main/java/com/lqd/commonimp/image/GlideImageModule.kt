package com.lqd.commonimp.image

import android.content.Context
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool
import com.bumptech.glide.load.engine.cache.LruResourceCache
import com.bumptech.glide.module.AppGlideModule
import com.bumptech.glide.request.RequestOptions

@GlideModule
class GlideImageModule : AppGlideModule() {
    override fun applyOptions(context: Context, builder: GlideBuilder) {
        super.applyOptions(context, builder)
        val memoryCacheSizeBytes = 1024L * 1024 * 100
        builder.setMemoryCache(LruResourceCache(memoryCacheSizeBytes))

        val bitmapPoolSizeBytes = 1024L * 1024 * 100
        builder.setBitmapPool(LruBitmapPool(bitmapPoolSizeBytes))

        val options = RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .format(DecodeFormat.PREFER_RGB_565).disallowHardwareConfig()
        builder.setDefaultRequestOptions(options)
    }
}