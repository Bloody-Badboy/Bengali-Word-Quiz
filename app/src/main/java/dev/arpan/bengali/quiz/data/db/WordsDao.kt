package dev.arpan.bengali.quiz.data.db

import android.database.Cursor
import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import dev.arpan.bengali.quiz.data.model.Word

@Dao
interface WordsDao {
    @Query(
        "SELECT * FROM ${Word.TABLE_NAME} ORDER BY RANDOM() LIMIT :limit"
    )
    suspend fun selectRandomWords(limit: Int): List<Word>

    @Query("UPDATE ${Word.TABLE_NAME} SET ${Word.COLUMN_BOOKMARKED} = :bookmark WHERE ${Word.COLUMN_ID} = :wordId")
    suspend fun bookmarkWord(wordId: Int, bookmark: Boolean): Int

    @Query("SELECT * FROM ${Word.TABLE_NAME} WHERE ${Word.COLUMN_BOOKMARKED} = 1 ORDER BY ${Word.COLUMN_ENGLISH_WORD} ASC")
    fun bookmarkedWordList(): PagingSource<Int, Word>

    @Query("SELECT * FROM ${Word.TABLE_NAME}")
    fun selectAll(): Cursor?

    @Query("SELECT * FROM ${Word.TABLE_NAME} WHERE ${Word.COLUMN_ID} = :id")
    fun selectById(id: Long): Cursor?
}