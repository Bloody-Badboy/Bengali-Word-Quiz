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

import android.content.ContentResolver
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import dev.arpan.bengali.quiz.WordQuizApplication
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class WordsContentProviderTest {

    private lateinit var contentResolver: ContentResolver

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<WordQuizApplication>()
        contentResolver = context.contentResolver
    }

    @Test
    fun loads_words_from_assets() {
        val cursor = contentResolver.query(
            WordsContentProvider.WORDS_CONTENT_URI,
            null,
            null,
            null,
            null
        )
        assertThat(cursor).isNotNull()
        assertThat(cursor?.count).isGreaterThan(1)
        cursor?.close()
    }

    @Test
    fun loads_words_from_asset() {
        val cursor = contentResolver.query(
            WordsContentProvider.BOOKMARKED_WORDS_CONTENT_URI,
            null,
            null,
            null,
            null
        )
        assertThat(cursor).isNotNull()
        cursor?.close()
    }
}
