package com.yly.androidallinone.view.paging

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lqd.commonimp.baseview.BaseLayoutActivity
import com.lqd.commonimp.extend.getColor
import com.lqd.commonimp.extend.log
import com.lqd.commonimp.recyclerview.BasePagingAdapter
import com.lqd.commonimp.recyclerview.WrapperRecyclerViewHolder
import com.yly.androidallinone.R
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.activity_test_paging.*
import kotlinx.android.synthetic.main.recycler_view_item.*
import org.jetbrains.anko.backgroundColor

class TestPaging : BaseLayoutActivity<PagingViewModel>(R.layout.activity_test_paging) {

    private val myAdapter by lazy {
        TestAdapterA(mContext)
    }

    override fun initView() {
        with(recyclerView) {
            layoutManager = LinearLayoutManager(mContext)
            adapter = myAdapter
            val viwe = View(mContext)
            viwe.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 100)
            viwe.backgroundColor = R.color.colorAccent.getColor()

            val viwe1 = View(mContext)
            viwe1.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 100)
            viwe1.backgroundColor = R.color.colorAccent.getColor()
//            myAdapter.addHeaderView(viwe)
//            myAdapter.addFooterView(viwe1)
        }
    }

    override fun initListener() {
        viewModel.dataList.observe(this, Observer {
            it?.let {
                myAdapter.submitList(it)
            }
        })

        refreshLayout.setOnRefreshListener {
            viewModel.refresh()
        }

        viewModel.refreshState.observe(this, Observer {
            it?.let {
                when (it) {
                    NetworkState.COMPLETE -> {
                        refreshLayout.finishRefresh()
                    }
                    else -> {

                    }
                }
            }
        })
    }

    override fun initData() {
        viewModel.testPaging()
        //

//        val origenLiveData = MutableLiveData<String>()
//        val sourceLiveData = origenLiveData
//        sourceLiveData.observe(this, Observer {
//            log(it)
//        }
//        )
//        origenLiveData.observe(this, Observer {
//            log(it)
//        })
//        origenLiveData.value = "xxx"
//
//    }

    }

    class TestAdapterA(mContext: Context) : BasePagingAdapter<String>(mContext, DIFF_CALLBACK) {
        override fun setItemData(holder: WrapperRecyclerViewHolder, position: Int, data: String?) {
            (holder as TestHolderA).setItemData(data = getItem(position))
        }

        override fun initViewHolder(parent: ViewGroup, viewType: Int): WrapperRecyclerViewHolder {
            val contentView = LayoutInflater.from(mContext).inflate(R.layout.recycler_view_item, parent, false)
            return TestHolderA(contentView)
        }

        class TestHolderA(override val containerView: View) : WrapperRecyclerViewHolder(containerView), LayoutContainer

        private fun TestHolderA.setItemData(data: String?) {
            textView.text = data
        }
    }
}

val DIFF_CALLBACK = object : DiffUtil.ItemCallback<String>() {
    override fun areItemsTheSame(p0: String, p1: String): Boolean {
        return true
    }

    override fun areContentsTheSame(p0: String, p1: String): Boolean {
        return p0 == p1
    }
}

