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

package dev.arpan.bengali.quiz.provider

import android.content.ContentProvider
import android.content.ContentResolver
import android.content.ContentUris
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import dev.arpan.bengali.quiz.data.db.AppDatabase
import dev.arpan.bengali.quiz.data.db.WordsDao
import dev.arpan.bengali.quiz.data.model.Word

class WordsContentProvider : ContentProvider() {

    companion object {
        private const val AUTHORITY = "dev.arpan.bengali.quiz.provider"

        private const val WORDS = Word.TABLE_NAME
        private const val BOOKMARKED_WORDS = "bookmarked_$WORDS"

        private val BASE_CONTENT_URI = Uri.parse("content://$AUTHORITY")

        val WORDS_CONTENT_URI: Uri = Uri.withAppendedPath(BASE_CONTENT_URI, WORDS)
        val BOOKMARKED_WORDS_CONTENT_URI: Uri = Uri.withAppendedPath(
            BASE_CONTENT_URI,
            BOOKMARKED_WORDS
        )

        private const val CODE_WORDS_DIR = 100
        private const val CODE_WORDS_ITEM = 101
        private const val CODE_BOOKMARKED_WORDS_DIR = 103
    }

    private val uriMatcher = UriMatcher(UriMatcher.NO_MATCH)

    private val wordsDao: WordsDao by lazy {
        AppDatabase.getInstance(checkNotNull(context)).wordsDao
    }

    init {
        uriMatcher.addURI(AUTHORITY, WORDS, CODE_WORDS_DIR)
        uriMatcher.addURI(AUTHORITY, "$WORDS/#", CODE_WORDS_ITEM)
        uriMatcher.addURI(AUTHORITY, BOOKMARKED_WORDS, CODE_BOOKMARKED_WORDS_DIR)
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        throw UnsupportedOperationException("")
    }

    override fun getType(uri: Uri) = when (uriMatcher.match(uri)) {
        CODE_WORDS_DIR -> ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + WORDS
        CODE_WORDS_ITEM -> ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + WORDS
        CODE_BOOKMARKED_WORDS_DIR -> ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + BOOKMARKED_WORDS
        else -> throw IllegalArgumentException("Unknown URI: $uri")
    }

    override fun onCreate() = true

    override fun query(
        uri: Uri,
        projection: Array<String>?,
        selection: String?,
        selectionArgs: Array<String>?,
        sortOrder: String?
    ): Cursor? {
        if (context == null) return null
        return when (uriMatcher.match(uri)) {
            CODE_WORDS_DIR -> {
                wordsDao.selectAll()
            }
            CODE_WORDS_ITEM -> {
                wordsDao.selectById(ContentUris.parseId(uri))
            }
            CODE_BOOKMARKED_WORDS_DIR -> {
                wordsDao.selectAllBookmarked()
            }
            else -> {
                throw UnsupportedOperationException("Unknown Uri: $uri")
            }
        }.also {
            it?.setNotificationUri(checkNotNull(context).contentResolver, uri)
        }
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        throw UnsupportedOperationException("")
    }

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<String>?
    ): Int {
        throw UnsupportedOperationException("")
    }
}
