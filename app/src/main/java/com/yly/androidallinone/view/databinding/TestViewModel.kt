package com.yly.androidallinone.view.databinding

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TestViewModel : ViewModel() {

    val personLv = MutableLiveData<Person>()

    fun testLvWithDb() {
        personLv.value = Person(id = 1, name = "yuliyang")
    }
}