package com.example.roulette.repository.database

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.roulette.repository.database.dao.RouletteDAO
import com.example.roulette.repository.database.dao.RouletteItemDAO

abstract class RouletteDatabase: RoomDatabase() {
    abstract fun rouletteDao(): RouletteDAO
    abstract fun rouletteItemDAO(): RouletteItemDAO

    companion object {
        private var INSTANCE: RouletteDatabase? = null

        fun getInstance(context: Context): RouletteDatabase? {
            if(INSTANCE == null) {
                synchronized(RouletteDatabase::class) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext, RouletteDatabase::class.java, "roulette.db")
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }

            return INSTANCE
        }

        fun destroyInstance() {
            INSTANCE == null
        }
    }

}