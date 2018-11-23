package com.yly.androidallinone.view.anko

import android.app.Activity
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.yly.androidallinone.R
import org.jetbrains.anko.*
import org.jetbrains.anko.constraint.layout.constraintLayout

fun Activity.getCusmView(): View {

    val ID_OK = 1
    return constraintLayout {
        imageView {
            id = ID_OK
            imageResource = R.drawable.test
        }.lparams(width = dip(90), height = dip(90)) {
            horizontalChainStyle = ConstraintLayout.LayoutParams.CHAIN_PACKED
        }
        textView("测试").lparams {
            startToEnd = ID_OK
        }
    }
}