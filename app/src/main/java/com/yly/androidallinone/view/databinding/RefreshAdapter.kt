package com.yly.androidallinone.view.databinding

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.databinding.BindingMethod
import androidx.databinding.BindingMethods
import com.scwang.smartrefresh.layout.SmartRefreshLayout

object RefreshAdapter {

    @JvmStatic
    @BindingAdapter("android:complete")
    fun setRefreshState(refreshView: SmartRefreshLayout, complete: Boolean) {
        if (complete) {
            refreshView.finishRefresh()
            refreshView.finishLoadMore()
        }
    }
}

@BindingMethods(value = arrayOf(BindingMethod(type = ImageView::class, method = "", attribute = "")))
class ImageViewBindingAdapter {}