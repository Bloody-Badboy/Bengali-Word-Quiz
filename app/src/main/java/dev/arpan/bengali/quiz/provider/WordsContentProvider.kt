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
        const val AUTHORITY = "dev.arpan.bengali.quiz.provider"
        val CONTENT_URI: Uri = Uri.parse("content://$AUTHORITY/${Word.TABLE_NAME}")

        private const val CODE_WORDS_DIR = 100
        private const val CODE_WORDS_ITEM = 101
    }

    private val uriMatcher = UriMatcher(UriMatcher.NO_MATCH)

    private val wordsDao: WordsDao by lazy {
        AppDatabase.getInstance(requireContext()).wordsDao
    }

    init {
        uriMatcher.addURI(AUTHORITY, Word.TABLE_NAME, CODE_WORDS_DIR)
        uriMatcher.addURI(AUTHORITY, Word.TABLE_NAME + "/#", CODE_WORDS_ITEM)
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        throw UnsupportedOperationException("")
    }

    override fun getType(uri: Uri) = when (uriMatcher.match(uri)) {
        CODE_WORDS_DIR -> ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + Word.TABLE_NAME
        CODE_WORDS_ITEM -> ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + Word.TABLE_NAME
        else -> throw IllegalArgumentException("Unknown URI: $uri")
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        throw UnsupportedOperationException("")
    }

    override fun onCreate() = true

    override fun query(
        uri: Uri, projection: Array<String>?, selection: String?,
        selectionArgs: Array<String>?, sortOrder: String?
    ): Cursor? {
        if (context == null) return null
        return when (uriMatcher.match(uri)) {
            CODE_WORDS_DIR -> {
                wordsDao.selectAll()
            }
            CODE_WORDS_ITEM -> {
                wordsDao.selectById(ContentUris.parseId(uri))
            }
            else -> {
                throw UnsupportedOperationException("")
            }
        }.also {
            it?.setNotificationUri(requireContext().contentResolver, uri)
        }
    }

    override fun update(
        uri: Uri, values: ContentValues?, selection: String?,
        selectionArgs: Array<String>?
    ): Int {
        throw UnsupportedOperationException("")
    }
}