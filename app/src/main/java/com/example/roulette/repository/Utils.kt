package com.example.roulette.repository

import com.example.roulette.repository.database.entity.RouletteItem
import java.lang.StringBuilder

object Utils {
    fun getRandom(maxNumber: Int): Int {
        val r = Math.random()
        return (r * maxNumber).toInt()
    }

    fun makeRouletteTitle(items: ArrayList<RouletteItem>): String {
        val title = StringBuilder()
        for(i in 0 until MIN_ITEM_COUNT) {
            title.append(items[i])
                .append(",")
        }

        title.deleteCharAt(title.lastIndex) //마지막 "," 제거

        if(items.size > MIN_ITEM_COUNT) {
            title.append(" 외 ${items.size - MIN_ITEM_COUNT}개")
        }

        return title.toString()
    }
}