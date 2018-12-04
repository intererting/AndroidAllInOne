package com.lqd.commonimp.recyclerview;

import android.view.View;

/**
 * recyclerView的item点击事件
 */
@FunctionalInterface
public interface MyItemClickListener<T> {
    void onItemClick(int position, View clickView, T data);
}
