package com.lqd.commonimp.model

data class LoadingDialogState(var isShowDialog: Boolean = false,
                              var loadingMsg: String? = null,
                              var cancelable: Boolean = false)