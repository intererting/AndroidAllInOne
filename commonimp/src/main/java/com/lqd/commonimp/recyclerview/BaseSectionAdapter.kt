package com.lqd.commonimp.recyclerview

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lqd.commonimp.model.SECTION_HEADER
import com.lqd.commonimp.model.SECTION_NORMAL
import com.lqd.commonimp.model.SectionModel

/**
 * 二级菜单Adapter
 */
abstract class BaseSectionAdapter<M>(mContext: Context, private val myItemClickListener: MyItemClickListener<M>)
    : BaseRecyclerAdapter<SectionModel<M>>(mContext) {

    private val formatMapDatas = hashMapOf<String, List<SectionModel<M>>?>()
    //头部布局
    abstract val headerlayoutRes: Int
    //item布局
    abstract val normalLayoutRes: Int

    override fun setItemData(holder: WrapperRecyclerViewHolder, position: Int, data: SectionModel<M>) {
        if (holder is WrapperSectionHolder<*>) {
            if (holder.type == SECTION_HEADER) {
                //头部
                holder.itemView.setOnClickListener {
                    val childDatas = formatMapDatas.get(data.headerName)
                    if (data.openMode) {
                        childDatas?.let {
                            removeAll(it, position)
                        }
                    } else {
                        childDatas?.let {
                            insertDatas(it, position)
                        }
                    }
                    data.openMode = !data.openMode
                    holder.changeMode(data.openMode)
                }
            } else {
                holder.itemView.setOnClickListener {
                    myItemClickListener.onItemClick(position, holder.itemView, data.data)
                }
            }
            (holder as WrapperSectionHolder<M>).setItemData(data.data)
        }
    }

    override fun initViewHolder(parent: ViewGroup, viewType: Int): WrapperRecyclerViewHolder {
        val view: View;
        if (viewType == SECTION_HEADER) {
            view = LayoutInflater.from(mContext).inflate(headerlayoutRes, parent, false)
            return initHeaderHolder(SECTION_HEADER, view)
        } else {
            view = LayoutInflater.from(mContext).inflate(normalLayoutRes, parent, false)
            return initNormalHolder(SECTION_NORMAL, view)
        }
    }

    override fun initItemViewType(position: Int): Int {
        val data = getDataList()[position]
        return data.type
    }


    abstract fun initHeaderHolder(type: Int, view: View): WrapperSectionHolder<M>

    abstract fun initNormalHolder(type: Int, view: View): WrapperSectionHolder<M>
}