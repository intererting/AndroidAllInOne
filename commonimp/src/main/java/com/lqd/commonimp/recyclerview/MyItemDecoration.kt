package com.lqd.commonimp.recyclerview

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView

/**
 * 自定义RecyclerView下划线，顶部加了间隔
 */
class MyItemDecoration(mContext: Context, orientation: Int) : DividerItemDecoration(mContext, orientation) {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)
        val position = parent.getChildAdapterPosition(view)
        if (position == 0) {
            outRect.top = outRect.bottom
        }
    }
}