package com.yly.androidallinone.testdialog

import com.lqd.commonimp.baseview.BaseLayoutActivity
import com.lqd.commonimp.client.BaseApplication
import com.lqd.commonimp.client.LiveDataBus
import com.lqd.commonimp.extend.addStatusBarFixView
import com.lqd.commonimp.model.LoadingDialogState
import com.yly.androidallinone.R
import kotlinx.android.synthetic.main.activity_test_c.*

class CActivity : BaseLayoutActivity<AViewModel>(R.layout.activity_test_c) {
    override fun initView() {
        addStatusBarFixView()
    }

    override fun initListener() {
        test.setOnClickListener {
            LiveDataBus.get().with(BaseApplication.provideInstance().topActivityInStack.localClassName)
                    .postValue(LoadingDialogState())
        }
    }

    override fun initData() {
    }
}