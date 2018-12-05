package com.yly.androidallinone.view.databinding

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.lqd.commonimp.baseview.BaseDataBindingActivity
import com.lqd.commonimp.extend.delayWithUI
import com.yly.androidallinone.R
import com.yly.androidallinone.databinding.ActivityListTestBinding
import kotlinx.android.synthetic.main.activity_list_test.*
import kotlinx.android.synthetic.main.recycler_view_item.*

class ListTestActivity : BaseDataBindingActivity<RefreshViewModel, ActivityListTestBinding>(R.layout.activity_list_test) {

    private val mAdapter by lazy {
        TestAdapter(this@ListTestActivity)
    }

    override fun initView() {
        with(recyclerView) {
            layoutManager = LinearLayoutManager(this@ListTestActivity)
            adapter = mAdapter
        }
        mBinding.viewModel = viewModel
        mBinding.setLifecycleOwner(this)
    }

    override fun initListener() {
    }

    override fun initData() {
        refreshView.setOnRefreshListener {
            delayWithUI(3000) {
                viewModel.testRefresh(0)
            }
//            val testDatas = arrayListOf<String>("1", "2", "3")
//            binding.datas = testDatas
//
//            delayWithUI(3000) {
//                binding.testNoUse = "hahaha"
//            }
        }
    }
}

class TestAdapter(mContext: Context) : BaseRecyclerAdapter<String>(mContext) {

    override fun setItemData(holder: WrapperRecyclerViewHolder, position: Int, data: String) {
        (holder as TestHolder).setItemData(data)
    }

    override fun initViewHolder(parent: ViewGroup, viewType: Int): WrapperRecyclerViewHolder {
        val contentView = LayoutInflater.from(mContext).inflate(R.layout.recycler_view_item, parent, false)
        return TestHolder(contentView)
    }

    class TestHolder(override val containerView: View) : WrapperRecyclerViewHolder(containerView)

    private fun TestHolder.setItemData(data: String) {
        textView.text = data
    }
}