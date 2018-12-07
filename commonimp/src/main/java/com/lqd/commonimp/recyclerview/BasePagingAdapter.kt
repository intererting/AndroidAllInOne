package com.lqd.commonimp.recyclerview

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.annotation.NonNull
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

abstract class BasePagingAdapter<M>(protected val mContext: Context,
                                    DIFF_CALLBACK: DiffUtil.ItemCallback<M>)
    : PagedListAdapter<M, BaseRecyclerViewHolder>(DIFF_CALLBACK) {

    private val headerLayout: LinearLayout by lazy {
        val layout = LinearLayout(mContext)
        with(layout) {
            orientation = LinearLayout.VERTICAL
            layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        }
        layout
    }
    private val footerLayout: LinearLayout by lazy {
        val layout = LinearLayout(mContext)
        with(layout) {
            layout.orientation = LinearLayout.VERTICAL
            layout.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        }
        layout
    }

    /**
     * 添加Header
     */
    fun addHeaderView(@NonNull headerView: View, index: Int = -1) {
        if (index < -1 || index > headerLayout.childCount) {
            throw  IllegalArgumentException("index越界")
        }
        headerLayout.addView(headerView, index)
        if (headerLayout.childCount == 1) {
            notifyDataSetChanged()
        } else {
            notifyItemChanged(0)
        }
    }

    /**
     * 获取指定HeaderView
     */
    fun getHeaderView(index: Int): View {
        if (index < 0 || index >= headerLayout.childCount) {
            throw  IllegalArgumentException("index越界")
        }
        return headerLayout.getChildAt(index)
    }

    /**
     * 在指定位置移除Header
     */
    fun removeHeaderViewAtIndex(index: Int) {
        if (index < 0 || index >= headerLayout.childCount) {
            throw  IllegalArgumentException("index越界")
        }
        headerLayout.removeViewAt(index)
        if (headerLayout.childCount == 0) {
            notifyDataSetChanged()
        } else {
            notifyItemChanged(0)
        }
    }

    /**
     * 添加Footer
     */
    fun addFooterView(@NonNull footerView: View) {
        footerLayout.addView(footerView, -1)
        notifyItemInserted(itemCount - 1)
    }

    final override fun getItemViewType(position: Int): Int {
        if (position == 0 && headerLayout.childCount > 0) {
            return HEADER_VIEW
        } else if (position == getHeaderCount() + super.getItemCount()) {
            return FOOTER_VIEW
        } else {
            val realPosition = position - getHeaderCount()
            return initItemViewType(realPosition)
        }
    }

    open fun initItemViewType(position: Int): Int {
        return super.getItemViewType(position)
    }

    final override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseRecyclerViewHolder {
        if (viewType == HEADER_VIEW) {
            return BaseRecyclerViewHolder(headerLayout)
        } else if (viewType == FOOTER_VIEW) {
            return BaseRecyclerViewHolder(footerLayout)
        } else {
            return initViewHolder(parent, viewType)
        }
    }

    override fun getItemCount(): Int {
        return getHeaderCount() + getFooterCount() + super.getItemCount()
    }

    final override fun onBindViewHolder(_holder: BaseRecyclerViewHolder, position: Int) {
        if (_holder is WrapperRecyclerViewHolder) {
            val realPosition = position - getHeaderCount()
            setItemData(_holder, realPosition, currentList!![realPosition])
        }
    }

    /**
     * 获取Footer数量
     */
    private fun getFooterCount() = if (footerLayout.childCount > 0) 1 else 0

    /**
     * 获取Header数量
     */
    private fun getHeaderCount() = if (headerLayout.childCount > 0) 1 else 0

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        val manager = recyclerView.layoutManager
        if (manager is GridLayoutManager) {
            manager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    val type = getItemViewType(position)
                    return if ((type == HEADER_VIEW) or (type == FOOTER_VIEW)) manager.spanCount else 1
                }
            }
        }
    }


    abstract fun setItemData(holder: WrapperRecyclerViewHolder, position: Int, data: M?)

    abstract fun initViewHolder(parent: ViewGroup, viewType: Int): WrapperRecyclerViewHolder

    companion object {
        //Adapter相关
        const val HEADER_VIEW = 1000
        const val FOOTER_VIEW = 1001
    }
}