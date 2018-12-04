package com.lqd.commonimp.recyclerview

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer

open class BaseRecyclerViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer

open class WrapperRecyclerViewHolder(override val containerView: View) : BaseRecyclerViewHolder(containerView)

abstract class WrapperSectionHolder<M>(val type: Int, itemView: View) : WrapperRecyclerViewHolder(itemView) {
    abstract fun changeMode(openMode: Boolean)

    abstract fun setItemData(data: M?)
}

