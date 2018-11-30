package com.yly.androidallinone.view.databinding

import android.widget.TextView
import androidx.databinding.BindingAdapter

object BindingAdapterTest {

    @JvmStatic
    @BindingAdapter("android:text")
    fun setText(view: TextView, text: CharSequence?) {
        val oldText = view.text
        if (text == oldText || (text == null && oldText.length == 0)) {
            return;
        }
        view.text = text;
    }

}
