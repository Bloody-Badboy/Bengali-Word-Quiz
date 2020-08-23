package dev.arpan.bengali.quiz.data.db

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import dev.arpan.bengali.quiz.data.model.Word

@Dao
interface WordsDao {
    @Query(
        "SELECT * FROM words ORDER BY RANDOM() LIMIT :limit"
    )
    suspend fun selectRandomWords(limit: Int): List<Word>

    @Query("UPDATE words SET bookmarked = :bookmark WHERE id = :wordId")
    suspend fun bookmarkWord(wordId: Int, bookmark: Boolean): Int

    @Query(
        "SELECT * FROM words WHERE bookmarked = 1 ORDER BY en_word ASC"
    )
    fun bookmarkedWordList(): PagingSource<Int, Word>
}