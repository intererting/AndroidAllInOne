package com.yly.androidallinone.view.jetpack

import com.lqd.commonimp.baseview.BaseLayoutActivity
import com.lqd.commonimp.db.model.SysInfo
import com.lqd.commonimp.extend.log
import com.lqd.httpclient.MyNetWorkObserver
import com.yly.androidallinone.R
import kotlinx.android.synthetic.main.activity_sys_info.*

class SysInfoActivity : BaseLayoutActivity<SysInfoViewModel>(R.layout.activity_sys_info) {

    override fun initView() {
    }

    override fun initListener() {

        test.setOnClickListener {
            viewModel.getSystemData().observe(this, object : MyNetWorkObserver<SysInfo>(showLoading = true) {
                override fun onComplete(data: SysInfo?, msg: String?) {
                    super.onComplete(data, msg)
                    data?.apply {
                        log(systemName)
                    }
                }
            })
        }
    }

    override fun initData() {
    }

}
