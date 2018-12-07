package com.yly.androidallinone

import com.lqd.commonimp.extend.md5Encode32
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
        assertEquals(4, 2 + 2)
    }
}

open class Father {
    companion object {
        @JvmStatic
        fun say() {
            println("xxx")
        }
    }
}

class Son : Father() {
}
