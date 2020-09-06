package com.example.roulette.repository.database.dao

import androidx.room.*
import com.example.roulette.repository.database.entity.RouletteItem

@Dao
interface RouletteItemDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(item: RouletteItem)

    @Update
    fun update(item: RouletteItem)

    @Delete
    fun delete(item: RouletteItem)
}