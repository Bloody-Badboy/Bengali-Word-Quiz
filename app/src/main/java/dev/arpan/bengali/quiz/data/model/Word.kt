package dev.arpan.bengali.quiz.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = Word.TABLE_NAME)
data class Word(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = COLUMN_ID)
    val id: Int = 0,

    @ColumnInfo(name = COLUMN_BENGALI_WORD)
    val bengali: String,

    @ColumnInfo(name = COLUMN_ENGLISH_WORD)
    val english: String,

    @ColumnInfo(name = COLUMN_BOOKMARKED)
    val bookmarked: Boolean
) {
    companion object {
        const val TABLE_NAME = "words"
        const val COLUMN_ID = "id"
        const val COLUMN_BENGALI_WORD = "bn_word"
        const val COLUMN_ENGLISH_WORD = "en_word"
        const val COLUMN_BOOKMARKED = "bookmarked"
    }
}