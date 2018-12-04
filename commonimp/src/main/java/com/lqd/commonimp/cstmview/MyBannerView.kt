package com.lqd.commonimp.cstmview

import android.content.Context
import android.os.Handler
import android.os.Message
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.lqd.commonimp.extend.screenWidth
import java.lang.ref.WeakReference

const val BANNER_NEXT = 0
const val BANNER_DELAY = 3000L

/**
 * 自定义Banner
 */
class MyBannerView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyle: Int = 0)
    : RecyclerView(context, attrs, defStyle) {

    var currentItem: Int = 0
    var listener: MyBannerViewListener? = null

    val mHandler by lazy {
        MyHandler(this)
    }

    init {
        val myLayoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        myLayoutManager.initialPrefetchItemCount = 2
        myLayoutManager.recycleChildrenOnDetach = true
        layoutManager = myLayoutManager
        PagerSnapHelper().attachToRecyclerView(this)
        setHasFixedSize(true)
        addOnScrollListener(object : OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                when (newState) {
                    SCROLL_STATE_IDLE -> {
                        val position = (layoutManager as LinearLayoutManager).findFirstCompletelyVisibleItemPosition()
                        currentItem = position
                        listener?.didPositionChanged(currentItem)
                    }
                }
            }
        })
    }

    /**
     * 开始banner切换
     */
    fun startAutoBanner() {
        if (!mHandler.hasMessages(BANNER_NEXT)) {
            mHandler.sendEmptyMessageDelayed(BANNER_NEXT, BANNER_DELAY)
            listener?.didPositionChanged(currentItem)
        }
    }

    /**
     * 停止banner切换
     */
    fun stopAutoBanner() {
        if (mHandler.hasMessages(BANNER_NEXT)) {
            mHandler.removeMessages(BANNER_NEXT)
        }
    }

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        when (ev.action) {
            MotionEvent.ACTION_DOWN -> {
                stopAutoBanner()
            }
            MotionEvent.ACTION_UP -> {
                startAutoBanner()
            }
        }
        return super.dispatchTouchEvent(ev)
    }

    override fun onAttachedToWindow() {
        startAutoBanner()
        super.onAttachedToWindow()
    }

    override fun onDetachedFromWindow() {
        stopAutoBanner()
        super.onDetachedFromWindow()
    }
}

class MyHandler(myBannerView: MyBannerView) : Handler() {

    private val screenWidth = myBannerView.context.screenWidth
    private val refBannerView = WeakReference<MyBannerView>(myBannerView)

    override fun handleMessage(msg: Message) {
        when (msg.what) {
            BANNER_NEXT -> {
                refBannerView.get()?.apply {
                    val position = (layoutManager as LinearLayoutManager).findFirstCompletelyVisibleItemPosition()
                    currentItem = position + 1
                    smoothScrollBy(screenWidth, 0)
                    mHandler.sendEmptyMessageDelayed(BANNER_NEXT, BANNER_DELAY)
                }
            }
        }
    }
}

interface MyBannerViewListener {
    fun didPositionChanged(position: Int)
}