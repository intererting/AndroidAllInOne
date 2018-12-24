package com.yly.androidallinone.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintSet
import androidx.transition.TransitionManager
import com.lqd.commonimp.extend.delayWithUI
import com.yly.androidallinone.R
import kotlinx.android.synthetic.main.activity_constraint.*

class ConstraintSet : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_constraint)
    }

    override fun onResume() {
        super.onResume()
        start.setOnClickListener {
            val constraintSet = ConstraintSet().apply {
                clone(this@ConstraintSet, R.layout.activity_constraint_end)
            }
            TransitionManager.beginDelayedTransition(rootView)
            constraintSet.applyTo(rootView)
        }
    }
}