package com.yly.androidallinone.view.databinding

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.lqd.commonimp.baseview.BaseViewModelActivity
import com.yly.androidallinone.R
import com.yly.androidallinone.base.view.BaseActivity
import com.yly.androidallinone.databinding.ActivityListTestBinding
import com.yly.androidallinone.extends.delayWithUI
import kotlinx.android.synthetic.main.activity_list_test.*
import kotlinx.android.synthetic.main.recycler_view_item.*

class ListTestActivity : BaseViewModelActivity<RefreshViewModel>() {

    private val mAdapter by lazy {
        TestAdapter(this@ListTestActivity)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityListTestBinding>(this
                , R.layout.activity_list_test)
        binding.viewModel = viewModel
        binding.setLifecycleOwner(this)
        initView()
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

    private fun initView() {
        val mAdapter = TestAdapter(this@ListTestActivity)
        with(recyclerView) {
            layoutManager = LinearLayoutManager(this@ListTestActivity)
            adapter = mAdapter
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