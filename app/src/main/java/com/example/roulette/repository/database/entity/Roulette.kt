package com.example.roulette.repository.database.entity

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Roulette")
data class Roulette(
    @PrimaryKey(autoGenerate = true)
    @NonNull
    val seq: Int = 0,

    var title: String
)