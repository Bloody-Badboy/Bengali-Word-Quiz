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
