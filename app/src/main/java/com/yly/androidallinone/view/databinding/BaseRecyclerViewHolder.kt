package com.yly.androidallinone.view.databinding

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer

open class BaseRecyclerViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer

open class WrapperRecyclerViewHolder(override val containerView: View) : BaseRecyclerViewHolder(containerView)



