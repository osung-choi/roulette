package com.example.roulette.repository.database.dao

import androidx.room.*
import com.example.roulette.repository.database.entity.Roulette
import io.reactivex.Completable
import io.reactivex.Maybe

@Dao
interface RouletteDAO {
    @Query("select * from Roulette")
    fun selectAll(): Maybe<List<Roulette>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(item: Roulette): Long

    @Update
    fun update(item: Roulette)

    @Delete
    fun delete(item: Roulette)
}