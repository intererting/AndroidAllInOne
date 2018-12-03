package com.yly.androidallinone.view.databinding

import android.widget.TextView
import androidx.databinding.BindingAdapter

object BindingAdapterTest {

    @JvmStatic
    @BindingAdapter(value = ["android:text", "android:noUse"])
    fun <T> setText(view: TextView, text: List<T>?, noUse: String?) {
        view.text = text.toString();
    }

}
