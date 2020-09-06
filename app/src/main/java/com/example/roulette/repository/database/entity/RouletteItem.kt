package com.example.roulette.repository.database.entity

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "RouletteItem",
    foreignKeys = [
        ForeignKey(
            entity = Roulette::class,
            parentColumns = ["seq"],
            childColumns = ["rouletteSeq"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class RouletteItem(
    @PrimaryKey(autoGenerate = true)
    @NonNull
    val seq: Int,

    val rouletteSeq: Int,

    //@Embedded //field가 object인 경우 선언
    val name: String
): Serializable