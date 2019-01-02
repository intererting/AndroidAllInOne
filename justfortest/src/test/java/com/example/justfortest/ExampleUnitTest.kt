package com.example.justfortest

import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
//        assertEquals(4, 2 + 2)
        val list = arrayListOf("1", "2", "3", "4")
//        val set = HashSet(list)
//        println(set)
        list.removeAll(arrayListOf("2", "3"))
        println(list)
    }
}
