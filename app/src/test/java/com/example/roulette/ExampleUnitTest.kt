package com.example.roulette

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

        val a = arrayListOf<MyTest>()
        val b = arrayListOf<MyTest>()

        a.add(MyTest(0,"가"))
        a.add(MyTest(1,"나"))
        a.add(MyTest(2,"다"))

        b.add(MyTest(0,"가"))
        b.add(MyTest(1,"다"))
        b.add(MyTest(2,"다"))


        assertArrayEquals(a.toArray(), b.toArray())
    }
}

data class MyTest(
    val num: Int,
    val text: String
)