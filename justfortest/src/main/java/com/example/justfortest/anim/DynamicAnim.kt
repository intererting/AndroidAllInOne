package com.example.justfortest.anim

import android.animation.Animator
import android.app.ActivityOptions
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.transition.Explode
import android.transition.TransitionInflater
import android.view.View
import android.view.ViewAnimationUtils
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.justfortest.R
import kotlinx.android.synthetic.main.activity_dynamic_anim.*


@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
class DynamicAnim : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val explode = TransitionInflater.from(this).inflateTransition(R.transition.explode);
        window.enterTransition = explode;
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dynamic_anim)
        test.setOnClickListener { v ->
            val slide = Explode()
            slide.duration = 200
            window.exitTransition = slide
            val mIntent = Intent(this, DynamicAnimA::class.java)
            mIntent.putExtra("transition", "explode");
            startActivity(mIntent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
            //            val anim = SpringAnimation(test, DynamicAnimation.X, 0f)
//                    .setStartVelocity(500f);
//            val spring = SpringForce(0f).setDampingRatio(SpringForce.DAMPING_RATIO_HIGH_BOUNCY)
//                    .setStiffness(SpringForce.STIFFNESS_LOW);
//            anim.spring = spring
//            anim.start();

//            val anim = FlingAnimation(test, DynamicAnimation.Y)
//            anim.setStartVelocity(5000f)
//                    .setMinValue(0f) // minimum translationX property
//                    .setMaxValue(1000f)  // maximum translationX property
//                    .setFriction(1f)
//                    .start();
//            anim.start()

            // 获取FloatingActionButton的中心点的坐标
//            val centerX = (v.left + v.right) / 2
//            val centerY = (v.top + v.bottom) / 2
//            // Math.hypot(x,y):  返回sqrt(x2 +y2)
//            // 获取扩散的半径
//            val finalRadius = Math.hypot(centerX.toDouble(), centerY.toDouble()).toFloat()
//            // 定义揭露动画
//            val mCircularReveal = ViewAnimationUtils.createCircularReveal(
//                    testA, centerX, centerY, 0f, finalRadius)
//            // 设置动画持续时间，并开始动画
//            mCircularReveal.addListener(object : Animator.AnimatorListener {
//                override fun onAnimationRepeat(animation: Animator?) {
//                }
//
//                override fun onAnimationEnd(animation: Animator?) {
//                }
//
//                override fun onAnimationCancel(animation: Animator?) {
//                }
//
//                override fun onAnimationStart(animation: Animator?) {
//                    testA.visibility = View.VISIBLE
//                }
//
//            })
//            mCircularReveal.setDuration(1000).start()
        }
    }
}