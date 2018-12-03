package com.yly.androidallinone.view.kotlin

open class ExtendFather {

}

class ExtendSon : ExtendFather() {

}

fun ExtendFather.f() {
    println("xxxxxxx")
}

fun main(args: Array<String>) {
    val son = ExtendSon()
    son.f()
}