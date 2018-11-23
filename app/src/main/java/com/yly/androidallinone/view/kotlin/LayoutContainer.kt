package com.yly.androidallinone.view.kotlin

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer

class ViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {
    fun setup(title: String) {
//        itemTitle.text = "Hello World!"
    }
}