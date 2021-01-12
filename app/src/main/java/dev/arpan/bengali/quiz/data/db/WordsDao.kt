/*
 * Copyright 2021 Arpan Sarkar
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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

    @Query("SELECT * FROM ${Word.TABLE_NAME} WHERE ${Word.COLUMN_BOOKMARKED} = 1 ORDER BY ${Word.COLUMN_ENGLISH_WORD} ASC")
    fun selectAllBookmarked(): Cursor?
}
