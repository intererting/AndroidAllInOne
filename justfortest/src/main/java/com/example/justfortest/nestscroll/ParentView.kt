package com.example.justfortest.nestscroll

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.LinearLayout
import androidx.core.view.NestedScrollingParent
import androidx.core.view.NestedScrollingParent2
import androidx.core.view.ViewCompat
import com.example.justfortest.R
import android.view.ViewGroup


class ParentView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0)
    : LinearLayout(context, attrs, defStyleAttr), NestedScrollingParent2 {
    override fun onStartNestedScroll(child: View, target: View, axes: Int, type: Int): Boolean {
        return (axes and ViewCompat.SCROLL_AXIS_VERTICAL) != 0
    }

    override fun onNestedPreScroll(target: View, dx: Int, dy: Int, consumed: IntArray, type: Int) {
        scrollBy(0, dy)
        findViewById<View>(R.id.headerView).offsetTopAndBottom(dy)
        consumed[1] = dy
    }

    override fun onNestedScroll(target: View, dxConsumed: Int, dyConsumed: Int, dxUnconsumed: Int, dyUnconsumed: Int, type: Int) {
        println("dyConsumed  ${dyConsumed}")
        println("dyUnconsumed  ${dyUnconsumed}")
    }

    override fun onStopNestedScroll(target: View, type: Int) {
        if (scaleY != 0F) {
            findViewById<View>(R.id.headerView).offsetTopAndBottom(-scrollY)
            scrollTo(0, 0)
        }
    }

    override fun onNestedScrollAccepted(child: View, target: View, axes: Int, type: Int) {
    }

    override fun onFinishInflate() {
        super.onFinishInflate()

    }

    override fun onStartNestedScroll(child: View, target: View, axes: Int): Boolean {
        return true
    }

    override fun onNestedFling(target: View, velocityX: Float, velocityY: Float, consumed: Boolean): Boolean {
        return true
    }

    override fun onNestedPreScroll(target: View, dx: Int, dy: Int, consumed: IntArray) {
//        target.offsetTopAndBottom(-dy)

    }

    override fun onNestedScroll(target: View, dxConsumed: Int, dyConsumed: Int, dxUnconsumed: Int, dyUnconsumed: Int) {
    }

    override fun onNestedPreFling(target: View, velocityX: Float, velocityY: Float): Boolean {
        return true
    }

    override fun onStopNestedScroll(target: View) {

    }

    override fun onNestedScrollAccepted(child: View, target: View, axes: Int) {
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        //不限制顶部的高度
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        setMeasuredDimension(measuredWidth, 2520)

    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                println("ACTION_DOWN")
            }
        }
        return super.onTouchEvent(event)
    }
}