package com.yly.androidallinone.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lqd.commonimp.baseview.BaseLayoutActivity
import com.yly.androidallinone.R
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.activity_test_paging.*
import kotlinx.android.synthetic.main.recycler_view_item.*

class TestPaging : BaseLayoutActivity<PagingViewModel>(R.layout.activity_test_paging) {

    private val myAdapter by lazy {
        TestAdapterA(mContext)
    }

    override fun initView() {
        with(recyclerView) {
            layoutManager = LinearLayoutManager(mContext)
            adapter = myAdapter
        }
    }

    override fun initListener() {
        viewModel.datas.observe(this, Observer {
            it?.let {
                myAdapter.submitList(it)
            }
        })
    }

    override fun initData() {
    }

}

class TestAdapterA(val mContext: Context) : PagedListAdapter<String, TestAdapterA.TestHolderA>(DIFF_CALLBACK) {
    override fun onCreateViewHolder(parent: ViewGroup, position: Int): TestHolderA {
        val contentView = LayoutInflater.from(mContext).inflate(R.layout.recycler_view_item, parent, false)
        return TestAdapterA.TestHolderA(contentView)
    }

    override fun onBindViewHolder(holder: TestHolderA, position: Int) {
    }

    class TestHolderA(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer

    private fun TestHolderA.setItemData(data: String) {
        textView.text = data
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
