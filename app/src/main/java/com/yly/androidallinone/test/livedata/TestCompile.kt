package com.yly.androidallinone.test.livedata

object TestCompile {

    var a: String = ""

    fun say() {

    }
}

fun main(args: Array<String>) {
    TestCompile.a = "xx"
    TestCompile.say()
}