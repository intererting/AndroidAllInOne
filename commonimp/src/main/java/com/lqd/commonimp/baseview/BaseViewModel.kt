package com.lqd.commonimp.baseview

import androidx.lifecycle.ViewModel
import com.lqd.commonimp.model.LoadingDialogState
import com.lqd.commonimp.util.SingleLiveEvent

abstract class BaseViewModel : ViewModel() {

    val errMsgLiveData by lazy { SingleLiveEvent<String>() }

    val infoMsgLiveData by lazy { SingleLiveEvent<String>() }

    val loadingDialogState by lazy { SingleLiveEvent<LoadingDialogState>() }
}