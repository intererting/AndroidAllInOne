package com.lqd.commonimp.client

import androidx.lifecycle.ViewModel
import com.lqd.commonimp.util.SingleLiveEvent

abstract class BaseViewModel : ViewModel() {

    val errMsgLiveData by lazy { SingleLiveEvent<String>() }

    val infoMsgLiveData by lazy { SingleLiveEvent<String>() }
}