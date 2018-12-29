package com.yly.androidallinone.test.other

import android.animation.Animator
import android.animation.ValueAnimator
import android.os.Bundle
import android.view.animation.LinearInterpolator
import androidx.appcompat.app.AppCompatActivity
import com.yly.androidallinone.R

class AnimationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_animation)
        val mAnimator = ValueAnimator.ofFloat(0f, 100f)
        mAnimator.interpolator = LinearInterpolator()
        mAnimator.duration = 1000
        mAnimator.addUpdateListener { animator -> println(animator.animatedValue.toString()) }
        mAnimator.addListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(animator: Animator?) {
            }

            override fun onAnimationCancel(animator: Animator?) {
            }

            override fun onAnimationStart(animator: Animator?) {
            }

            override fun onAnimationEnd(animator: Animator?) {
                mAnimator.setFloatValues(0f, 1000f)
//                mAnimator.start()
            }

        })
        mAnimator.start()
    }
}