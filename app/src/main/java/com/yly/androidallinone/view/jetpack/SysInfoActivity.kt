package com.yly.androidallinone.view.jetpack

import com.lqd.commonimp.baseview.BaseLayoutActivity
import com.lqd.commonimp.db.model.SysInfo
import com.lqd.commonimp.extend.log
import com.lqd.httpclient.MyNetWorkObserver
import com.yly.androidallinone.R

class SysInfoActivity : BaseLayoutActivity<SysInfoViewModel>(R.layout.activity_sys_info) {

    override fun initView() {
    }

    override fun initListener() {
    }

    override fun initData() {
        viewModel.getSystemData().observe(this, object : MyNetWorkObserver<SysInfo>() {
            override fun onComplete(data: SysInfo?, msg: String?) {
                super.onComplete(data, msg)
                data?.apply {
                    log(systemName)
                }
            }
        })
    }

}
