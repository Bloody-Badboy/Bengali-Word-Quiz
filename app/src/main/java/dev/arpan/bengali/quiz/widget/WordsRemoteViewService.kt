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

package dev.arpan.bengali.quiz.widget

import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.os.Binder
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import dev.arpan.bengali.quiz.R
import dev.arpan.bengali.quiz.data.model.Word
import dev.arpan.bengali.quiz.provider.WordsContentProvider

class WordsRemoteViewService : RemoteViewsService() {

    override fun onGetViewFactory(intent: Intent?): RemoteViewsFactory {
        return BookmarkedWordsListRemoteViewsFactory(applicationContext)
    }

    private class BookmarkedWordsListRemoteViewsFactory(private val applicationContext: Context) :
        RemoteViewsFactory {

        private var cursor: Cursor? = null

        override fun onCreate() {
        }

        override fun onDataSetChanged() {
            cursor?.close()
            val identityToken = Binder.clearCallingIdentity()
            cursor = applicationContext.contentResolver.query(
                WordsContentProvider.BOOKMARKED_WORDS_CONTENT_URI,
                null,
                null,
                null,
                null
            )
            Binder.restoreCallingIdentity(identityToken)
        }

        override fun onDestroy() {
            cursor?.close()
        }

        override fun getCount() = cursor?.count ?: 0

        override fun getViewAt(position: Int): RemoteViews? {
            val c = cursor ?: return null
            if (position < 0 || !c.moveToPosition(position)) return null

            return RemoteViews(
                applicationContext.packageName,
                R.layout.item_widget_bookmarked_word
            ).apply {
                setTextViewText(
                    R.id.tv_bn_word,
                    c.getString(c.getColumnIndexOrThrow(Word.COLUMN_BENGALI_WORD))
                )
                setTextViewText(
                    R.id.tv_en_word,
                    c.getString(c.getColumnIndexOrThrow(Word.COLUMN_ENGLISH_WORD))
                )
            }
        }

        override fun getLoadingView() = null

        override fun getViewTypeCount() = 1

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun hasStableIds() = true
    }
}
