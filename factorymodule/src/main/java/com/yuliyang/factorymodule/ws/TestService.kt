package com.yuliyang.factorymodule.ws

import androidx.lifecycle.LiveData

interface TestService {

    fun test(id: Int): LiveData<String>
}