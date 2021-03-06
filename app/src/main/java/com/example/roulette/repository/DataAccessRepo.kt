package com.example.roulette.repository

import android.content.Context
import com.example.roulette.repository.database.RouletteDatabase
import com.example.roulette.repository.database.entity.Roulette
import com.example.roulette.repository.database.entity.RouletteItem

class DataAccessRepo(
    private val context: Context
) {
    private val db by lazy {
        RouletteDatabase.getInstance(context)
    }

    fun insertRoulette(item: Roulette) =
        db.rouletteDao()
            .insert(item)

    fun insertRouletteItems(list: ArrayList<RouletteItem>) =
        db.rouletteItemDAO()
            .insertAll(list)

    fun selectAllRoulette() =
        db.rouletteDao()
            .selectAll()

    fun deleteRoulette(item: Roulette) =
        db.rouletteDao()
            .delete(item)

    fun selectRouletteItem(rouletteSeq: Int) =
        db.rouletteItemDAO()
            .selectRouletteItem(rouletteSeq)

    fun selectMaxItemSeq() =
        db.rouletteItemDAO()
            .selectMaxSeq()

}