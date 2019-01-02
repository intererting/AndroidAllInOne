package com.example.justfortest

import android.annotation.TargetApi
import android.os.Build
import android.os.Bundle
import android.transition.ChangeBounds
import android.transition.TransitionManager
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_transaction.*

class Transaction : AppCompatActivity() {

    @TargetApi(Build.VERSION_CODES.KITKAT)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transaction)
        start.setOnClickListener {
            val changeBounds = ChangeBounds()
            changeBounds.duration = 2000
            TransitionManager.beginDelayedTransition(rootView, changeBounds)
//            val layoutParams = image.layoutParams
//            layoutParams.height = 400
//            layoutParams.width = 400
//            image.layoutParams = layoutParams


        }
    }

}