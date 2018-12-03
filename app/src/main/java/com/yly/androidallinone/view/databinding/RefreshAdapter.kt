package com.yly.androidallinone.view.databinding

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.databinding.BindingMethod
import androidx.databinding.BindingMethods
import androidx.recyclerview.widget.RecyclerView
import com.scwang.smartrefresh.layout.SmartRefreshLayout

object RefreshAdapter {


    @JvmStatic
    @BindingAdapter("android:complete")
    fun setRefreshState(refreshView: SmartRefreshLayout, complete: Boolean?) {
        if (null == complete) {
            return
        }
        if (complete) {
            refreshView.finishRefresh()
            refreshView.finishLoadMore()
        }
    }

    @JvmStatic
    @BindingAdapter(value = ["android:isRefresh", "android:listDatas"])
    fun <DataType> setListData(listView: RecyclerView, isRefresh: Boolean?, listDatas: List<DataType>?) {
        if (null != isRefresh && null != listDatas) {
            val adapter = listView.adapter as BaseRecyclerAdapter<DataType>
            if (isRefresh) {
                adapter.addRefreshDatas(listDatas)
            } else {
                adapter.addLoadMoreDatas(listDatas)
            }
        }
    }
}

@BindingMethods(value = arrayOf(BindingMethod(type = ImageView::class, method = "", attribute = "")))
class ImageViewBindingAdapter {}
