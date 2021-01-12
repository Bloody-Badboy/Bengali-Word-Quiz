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
            WordsContentProvider.CONTENT_URI,
            null,
            null,
            null,
            null
        )
        assertThat(cursor).isNotNull()
        assertThat(cursor?.count).isGreaterThan(1)
        cursor?.close()
    }
}