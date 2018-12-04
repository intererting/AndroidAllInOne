package com.lqd.commonimp.recyclerview

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.lqd.commonimp.R
import com.lqd.commonimp.extend.getDimension

/**
 * 表格布局分割线
 */
class MyGridItemDecoration(val mContext: Context) : RecyclerView.ItemDecoration() {

    private val screenMargin: Int = R.dimen.screen_margin.getDimension().toInt()
    private val halfScreenMargin: Int = R.dimen.screen_margin.getDimension().toInt() / 2

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val layoutManager = parent.layoutManager
//        val childCount = state.itemCount
        if (layoutManager is GridLayoutManager) {
            val spanCount = layoutManager.spanCount
            val spanSize = layoutManager.spanSizeLookup.getSpanSize(parent.getChildLayoutPosition(view))
            val position = parent.getChildAdapterPosition(view)
            if (spanSize == 1) {
                if (isFirstColum(position, spanCount)) {
                    outRect.set(screenMargin, screenMargin, halfScreenMargin, 0)
                } else if (isLastColum(parent, position, spanCount, parent.childCount)) {
                    outRect.set(halfScreenMargin, screenMargin, screenMargin, 0)
                } else {
                    outRect.set(halfScreenMargin, screenMargin, halfScreenMargin, 0)
                }
            } else {
                outRect.set(screenMargin, screenMargin, screenMargin, 0)
            }
        } else if (layoutManager is StaggeredGridLayoutManager) {
            val spanCount = layoutManager.spanCount
            val position = parent.getChildAdapterPosition(view)
            if (isFirstColum(position, spanCount)) {
                outRect.set(screenMargin, screenMargin, halfScreenMargin, 0)
            } else if (isLastColum(parent, position, spanCount, parent.childCount)) {
                outRect.set(halfScreenMargin, screenMargin, screenMargin, 0)
            } else {
                outRect.set(halfScreenMargin, screenMargin, screenMargin, 0)
            }
        }
    }

    /**
     * 是否是第一列
     */
    private fun isFirstColum(pos: Int, spanCount: Int): Boolean {
        return pos % spanCount == 0
    }

    private fun isLastColum(parent: RecyclerView, pos: Int, spanCount: Int, childCount: Int): Boolean {
        val layoutManager = parent.layoutManager
        if (layoutManager is GridLayoutManager) {
            return (pos + 1) % spanCount == 0
        } else if (layoutManager is StaggeredGridLayoutManager) {
            val orientation = layoutManager.orientation
            if (orientation == StaggeredGridLayoutManager.VERTICAL) {
                return (pos + 1) % spanCount == 0
            } else {
                val lastChild = childCount - childCount % spanCount
                return pos >= lastChild
            }
        }
        return false
    }

    private fun isLastRaw(parent: RecyclerView, pos: Int, spanCount: Int, childCount: Int): Boolean {
        val layoutManager = parent.layoutManager
        if (layoutManager is GridLayoutManager) {
            val lastChild = childCount - childCount % spanCount
            return pos >= lastChild
        } else if (layoutManager is StaggeredGridLayoutManager) {
            val orientation = layoutManager.orientation
            if (orientation == StaggeredGridLayoutManager.VERTICAL) {
                val lastChild = childCount - childCount % spanCount
                return pos >= lastChild
            } else {
                // 如果是最后一行，则不需要绘制底部
                return (pos + 1) % spanCount == 0
            }
        }
        return false
    }
}