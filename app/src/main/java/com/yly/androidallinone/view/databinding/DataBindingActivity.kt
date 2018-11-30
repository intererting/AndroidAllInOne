package com.yly.androidallinone.view.databinding

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.yly.androidallinone.R
import com.yly.androidallinone.base.view.BaseActivity
import com.yly.androidallinone.databinding.ActivityDataBindingBinding

class DataBindingActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityDataBindingBinding>(this
                , R.layout.activity_data_binding)
        val viewModel = ViewModelProviders.of(this).get(TestViewModel::class.java)
        binding.viewModel = viewModel
        viewModel.testLvWithDb()
    }
}