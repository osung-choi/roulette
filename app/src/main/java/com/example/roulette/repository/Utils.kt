package com.example.roulette.repository

object Utils {
    fun getRandom(maxNumber: Int): Int {
        val r = Math.random()
        return (r * maxNumber).toInt()
    }
}