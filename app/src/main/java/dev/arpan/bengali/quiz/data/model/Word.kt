package dev.arpan.bengali.quiz.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "words")
data class Word(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo(name = "bn_word")
    val bengali: String,

    @ColumnInfo(name = "en_word")
    val english: String,

    @ColumnInfo(name = "bookmarked")
    val bookmarked: Boolean
)