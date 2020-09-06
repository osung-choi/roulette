package com.example.roulette.repository.database.dao

import androidx.room.*
import com.example.roulette.repository.database.entity.Roulette

@Dao
interface RouletteDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(item: Roulette)

    @Update
    fun update(item: Roulette)

    @Delete
    fun delete(item: Roulette)
}