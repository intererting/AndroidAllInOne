package com.yly.androidallinone.view.databinding

import androidx.lifecycle.MutableLiveData

class Person() {

    val idLV = MutableLiveData<Int>()
    val nameLV = MutableLiveData<String>()

    constructor(id: Int, name: String) : this() {
        idLV.value = id
        nameLV.value = name
    }
}