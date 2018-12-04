package com.lqd.commonimp.recyclerview

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.LinearLayout
import android.widget.LinearLayout.VERTICAL
import androidx.annotation.NonNull
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lqd.commonimp.R
import com.lqd.commonimp.model.PageInfo
import org.jetbrains.annotations.NotNull
import java.util.*

/**
 * 基本的Adapter
 */
abstract class BaseRecyclerAdapter<M>(protected val mContext: Context) : RecyclerView.Adapter<BaseRecyclerViewHolder>() {

    //空布局
    var emptyView: View? = null
    //是否显示空布局
    var showEmptyView = true
    //有Header是否显示EmptyView
    var showEmptyViewWithHeaderFooter = false
    //分页信息
    val pageInfo = PageInfo()

    private val dataList: MutableList<M> = arrayListOf()

    fun getDataList(): List<M> {
        return Collections.unmodifiableList(dataList)
    }

    private val headerLayout: LinearLayout by lazy {
        val layout = LinearLayout(mContext)
        layout.orientation = VERTICAL
        layout.layoutParams = ViewGroup.LayoutParams(MATCH_PARENT, WRAP_CONTENT)
        return@lazy layout
    }
    private val footerLayout: LinearLayout by lazy {
        val layout = LinearLayout(mContext)
        layout.orientation = VERTICAL
        layout.layoutParams = ViewGroup.LayoutParams(MATCH_PARENT, WRAP_CONTENT)
        return@lazy layout
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
            notifyItemInserted(0)
            notifyItemRangeChanged(0, itemCount)
        } else {
            notifyItemChanged(0)
        }
    }

    /**
     * 获取指定HeaderView
     */
    fun getHeaderView(index: Int): View {
        if (index < 0 || index >= headerLayout.childCount) {
            return View(mContext)
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
            notifyItemRemoved(0)
            notifyItemRangeChanged(0, itemCount)
        } else {
            notifyItemChanged(0)
        }
    }

    /**
     * 添加Footer
     */
    fun addFooterView(@NonNull footerView: View) {
        footerLayout.addView(footerView, -1)
        if (footerLayout.childCount == 1) {
            val position = getHeaderCount() + getEmptyCount() + dataList.size
            notifyItemInserted(position)
            notifyItemRangeChanged(position, itemCount - position)
        } else {
            notifyItemChanged(getHeaderCount() + getEmptyCount() + dataList.size)
        }
    }

    final override fun getItemViewType(position: Int): Int {
        if (position == 0 && headerLayout.childCount > 0) {
            return HEADER_VIEW
        } else if (shouldShowEmptyView() && position == getHeaderCount()) {
            return EMPTY_VIEW
        } else if (position == getHeaderCount() + getEmptyCount() + dataList.size) {
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
        if (viewType == EMPTY_VIEW) {
            return if (emptyView != null) BaseRecyclerViewHolder(emptyView!!) else
                BaseRecyclerViewHolder(LayoutInflater.from(mContext).inflate(R.layout.list_empty, parent, false))
        } else if (viewType == HEADER_VIEW) {
            return BaseRecyclerViewHolder(headerLayout)
        } else if (viewType == FOOTER_VIEW) {
            return BaseRecyclerViewHolder(footerLayout)
        } else {
            return initViewHolder(parent, viewType)
        }
    }

    override fun getItemCount(): Int {
        return getHeaderCount() + getFooterCount() + if (shouldShowEmptyView()) 1 else dataList.size
    }

    final override fun onBindViewHolder(_holder: BaseRecyclerViewHolder, position: Int) {
        if (_holder is WrapperRecyclerViewHolder) {
            val realPosition = position - getHeaderCount()
            setItemData(_holder, realPosition, dataList[realPosition])
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

    private fun getEmptyCount() = if (shouldShowEmptyView()) 1 else 0

    private fun shouldShowEmptyView(): Boolean {
        val result: Boolean
        if (showEmptyViewWithHeaderFooter) {
            result = dataList.size == 0
        } else {
            result = getHeaderCount() + getFooterCount() + dataList.size == 0
        }
        return showEmptyView && result
    }

    /**
     * 添加数据
     */
    fun addMoreDatas(@NotNull newDatas: List<M>) {
        dataList.addAll(newDatas)
        notifyItemRangeInserted(dataList.size - newDatas.size + 1 + getHeaderCount(), newDatas.size)
        //更新pageInfo
        pageInfo.pageNo = dataList.size / 20
    }

    fun insertDatas(@NotNull newDatas: List<M>, startPosition: Int) {
        dataList.addAll(startPosition + 1, newDatas)
        notifyItemRangeInserted(startPosition + 1, newDatas.size)
        notifyItemRangeChanged(startPosition + 1, dataList.size - startPosition - 1)
    }

    fun removeAll(@NotNull datas: List<M>, startPosition: Int) {
        dataList.removeAll(datas)
        notifyItemRangeRemoved(startPosition + 1, datas.size)
        notifyItemRangeChanged(startPosition + 1, dataList.size - startPosition - 1)
    }

    /**
     * 添加刷新数据
     */
    fun addNewDatas(@NotNull newDatas: List<M>) {
        dataList.clear()
        dataList.addAll(newDatas)
        notifyDataSetChanged()
        pageInfo.reset()
    }

    /**
     * 更新数据
     */
    fun updateData(updatePosition: Int, newData: M) {
        dataList[updatePosition] = newData
        notifyItemChanged(updatePosition + getHeaderCount())
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        val manager = recyclerView.layoutManager
        if (manager is GridLayoutManager) {
            manager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    val type = getItemViewType(position)
                    return if ((type == EMPTY_VIEW) or (type == HEADER_VIEW) or (type == FOOTER_VIEW)) manager.spanCount else 1
                }
            }
        }
    }


    abstract fun setItemData(holder: WrapperRecyclerViewHolder, position: Int, data: M)

    abstract fun initViewHolder(parent: ViewGroup, viewType: Int): WrapperRecyclerViewHolder

    companion object {
        //Adapter相关
        const val EMPTY_VIEW = 100
        const val HEADER_VIEW = 1000
        const val FOOTER_VIEW = 1001
    }
}
