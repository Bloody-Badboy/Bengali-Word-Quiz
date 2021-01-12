package dev.arpan.bengali.quiz.data

import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import dev.arpan.bengali.quiz.WORDS
import dev.arpan.bengali.quiz.data.db.WordsDao
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class DefaultWordsRepositoryTest {
    private val wordsDao: WordsDao = mock()

    private lateinit var wordsRepository: WordsRepository

    @Before
    fun setUp() {
        wordsRepository = DefaultWordsRepository(
            wordsDao = wordsDao,
            dispatcher = TestCoroutineDispatcher()
        )
    }

    @Test
    fun `getQuizWord returns null when word list empty`() = runBlockingTest {
        whenever(wordsDao.selectRandomWords(any())).thenReturn(emptyList())
        val quizWordItem = wordsRepository.getQuizWord(true, 10)
        assertThat(quizWordItem).isNull()
    }

    @Test
    fun `getQuizWord returns word item with correct word`() = runBlockingTest {
        whenever(wordsDao.selectRandomWords(any())).thenReturn(WORDS.subList(0, 4))

        var quizWordItem = wordsRepository.getQuizWord(true, 4)
        assertThat(quizWordItem).isNotNull()
        assertThat(quizWordItem!!.options.size).isEqualTo(4)
        assertThat(quizWordItem.word).isEqualTo(WORDS[0].english)

        quizWordItem = wordsRepository.getQuizWord(false, 4)
        assertThat(quizWordItem).isNotNull()
        assertThat(quizWordItem!!.options.size).isEqualTo(4)
        assertThat(quizWordItem.word).isEqualTo(WORDS[0].bengali)
    }

    @Test
    fun `addWordToBookmark returns true when word bookmarked successfully`() = runBlockingTest {
        whenever(wordsDao.bookmarkWord(any(), any())).thenReturn(1)
        val added = wordsRepository.addWordToBookmark(1)
        verify(wordsDao).bookmarkWord(1, true)
        assertThat(added).isTrue()
    }

    @Test
    fun `addWordToBookmark returns false when bookmark fail`() = runBlockingTest {
        whenever(wordsDao.bookmarkWord(any(), any())).thenReturn(0)
        val added = wordsRepository.addWordToBookmark(1)
        verify(wordsDao).bookmarkWord(1, true)
        assertThat(added).isFalse()
    }

    @Test
    fun `removeWordToBookmark returns true when word removed successfully`() = runBlockingTest {
        whenever(wordsDao.bookmarkWord(any(), any())).thenReturn(1)
        val added = wordsRepository.removeWordFromBookmark(1)
        verify(wordsDao).bookmarkWord(1, false)
        assertThat(added).isTrue()
    }

    @Test
    fun `removeWordToBookmark returns false when remove fail`() = runBlockingTest {
        whenever(wordsDao.bookmarkWord(any(), any())).thenReturn(0)
        val added = wordsRepository.removeWordFromBookmark(1)
        verify(wordsDao).bookmarkWord(1, false)
        assertThat(added).isFalse()
    }
}