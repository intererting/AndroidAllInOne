package com.yly.androidallinone.view.databinding

import android.os.Bundle
import android.os.Handler
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.yly.androidallinone.R
import com.yly.androidallinone.base.view.BaseActivity
import com.yly.androidallinone.databinding.ActivityListTestBinding
import com.yly.androidallinone.extends.delayWithUI
import kotlinx.android.synthetic.main.activity_list_test.*

class ListTestActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityListTestBinding>(this
                , R.layout.activity_list_test)
        val viewModel = ViewModelProviders.of(this).get(RefreshViewModel::class.java)
        binding.viewModel = viewModel
        binding.setLifecycleOwner(this)
        refreshView.setOnRefreshListener {
            delayWithUI(3000) {
                viewModel.testRefresh()
            }
        }

    }
}