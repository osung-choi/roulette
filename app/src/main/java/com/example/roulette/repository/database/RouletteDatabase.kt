package com.example.roulette.repository.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.roulette.repository.database.dao.RouletteDAO
import com.example.roulette.repository.database.dao.RouletteItemDAO
import com.example.roulette.repository.database.entity.Roulette
import com.example.roulette.repository.database.entity.RouletteItem

@Database(
    entities = [Roulette::class, RouletteItem::class],
    version = 1)
abstract class RouletteDatabase: RoomDatabase() {
    abstract fun rouletteDao(): RouletteDAO
    abstract fun rouletteItemDAO(): RouletteItemDAO

    companion object {
        @Volatile private var INSTANCE: RouletteDatabase? = null

        fun getInstance(context: Context): RouletteDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(context.applicationContext, RouletteDatabase::class.java, "roulette.db")
            .fallbackToDestructiveMigration()
            .build()
    }

}