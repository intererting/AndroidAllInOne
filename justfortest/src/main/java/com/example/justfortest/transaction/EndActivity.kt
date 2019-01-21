package com.example.justfortest.transaction

import android.os.Build
import android.os.Bundle
import android.transition.TransitionSet
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.SharedElementCallback
import androidx.core.view.ViewCompat
import com.example.justfortest.R
import kotlinx.android.synthetic.main.activity_anim_end.*

class EndActivity : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_anim_end)
        ViewCompat.setTransitionName(back, "buttonShare");
        ActivityCompat.setEnterSharedElementCallback(this, object : SharedElementCallback() {
            override fun onMapSharedElements(names: MutableList<String>, sharedElements: MutableMap<String, View>) {
                super.onMapSharedElements(names, sharedElements)
            }
        });

//        val transitionSet = TransitionSet();
//
//        //改变view的位置
//        val changePositionTransition = new ChangePositionTransition ();
//        val colorTransition = ColorTransition(getResources().getColor(R.color.colorPrimary), getResources().getColor(R.color.colorAccent));
//        //view做RevealTransition
//        CircleShareElemEnterTransition shareElemEnterRevealTransition = new CircleShareElemEnterTransition(rightTop);
//
//        transitionSet.addTransition(shareElemEnterRevealTransition);
//        transitionSet.addTransition(colorTransition);
//        transitionSet.addTransition(changePositionTransition);

//        transitionSet.addTarget(R.id.back);
//        transitionSet.setDuration(5000);
//        getWindow().setSharedElementEnterTransition(transitionSet);

    }
}