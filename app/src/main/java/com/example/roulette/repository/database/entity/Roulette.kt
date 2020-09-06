package com.example.roulette.repository.database.entity

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "Roulette")
data class Roulette(
    @PrimaryKey(autoGenerate = true)
    @NonNull
    val seq: Int = 0,

    var title: String
):Serializable