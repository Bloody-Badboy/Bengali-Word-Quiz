package dev.arpan.bengali.quiz.ui.quiz

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.atLeastOnce
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import dev.arpan.bengali.quiz.MainCoroutineRule
import dev.arpan.bengali.quiz.QUIZ_WORD_ITEM
import dev.arpan.bengali.quiz.QUIZ_WORD_ITEM_BOOKMARKED
import dev.arpan.bengali.quiz.data.WordsRepository
import dev.arpan.bengali.quiz.getOrAwaitValue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class QuizViewModelTest {

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val wordsRepository: WordsRepository = mock()

    private fun initViewModel(words: Int = 2) = QuizViewModel(wordsRepository).apply {
        isEnglishToBengali = true
        numberOfOptions = 4
        numberOfWords = words
    }

    @Test
    fun `getQuestion() emits quiz word`() = runBlockingTest {
        val quizViewModel = initViewModel()
        whenever(wordsRepository.getQuizWord(any(), any())).thenReturn(QUIZ_WORD_ITEM)
        quizViewModel.getQuestion()
        quizViewModel.quizWord.getOrAwaitValue()
    }

    @Test
    fun `isLastWord = true when word ends`() = runBlockingTest {
        val quizViewModel = initViewModel(words = 1)
        whenever(wordsRepository.getQuizWord(any(), any())).thenReturn(QUIZ_WORD_ITEM_BOOKMARKED)
        quizViewModel.getQuestion()
        val isLastWord = quizViewModel.isLastWord.getOrAwaitValue()
        assertThat(isLastWord).isTrue()
    }

    @Test
    fun `currentWordIndex updates when word fetched`() = runBlockingTest {
        val quizViewModel = initViewModel()
        whenever(wordsRepository.getQuizWord(any(), any())).thenReturn(QUIZ_WORD_ITEM_BOOKMARKED)
        quizViewModel.getQuestion()
        val prevIndex = quizViewModel.currentWordIndex.getOrAwaitValue()
        quizViewModel.getQuestion()
        val currentIndex = quizViewModel.currentWordIndex.getOrAwaitValue()
        assertThat(currentIndex).isEqualTo(prevIndex + 1)
    }

    @Test
    fun `isBookmarked = true when fetched word is bookmarked`() = runBlockingTest {
        val quizViewModel = initViewModel()
        whenever(wordsRepository.getQuizWord(any(), any())).thenReturn(QUIZ_WORD_ITEM_BOOKMARKED)
        quizViewModel.getQuestion()
        val isBookmarked = quizViewModel.isBookmarked.getOrAwaitValue()
        assertThat(isBookmarked).isTrue()
    }

    @Test
    fun `isBookmarked = false when word is fetched not bookmarked`() =
        mainCoroutineRule.runBlockingTest {
            val quizViewModel = initViewModel()
            whenever(wordsRepository.getQuizWord(any(), any())).thenReturn(QUIZ_WORD_ITEM)
            quizViewModel.getQuestion()
            val isBookmarked = quizViewModel.isBookmarked.getOrAwaitValue()
            assertThat(isBookmarked).isFalse()
        }

    @Test
    fun `isBookmarked = true when word bookmarked successfully`() = runBlockingTest {
        val quizViewModel = initViewModel()
        whenever(wordsRepository.addWordToBookmark(any())).thenReturn(true)
        quizViewModel.bookmarkWord(1, true)
        val isBookmarked = quizViewModel.isBookmarked.getOrAwaitValue()
        assertThat(isBookmarked).isTrue()
    }

    @Test
    fun `isBookmarked = false when word bookmarked removed`() = runBlockingTest {
        val quizViewModel = initViewModel()
        whenever(wordsRepository.removeWordFromBookmark(any())).thenReturn(true)
        quizViewModel.bookmarkWord(1, false)
        val isBookmarked = quizViewModel.isBookmarked.getOrAwaitValue()
        assertThat(isBookmarked).isFalse()
    }

    @Test
    fun `showFinishedDialog when onNextWordClick() and word ends`() = runBlockingTest {
        val quizViewModel = initViewModel(words = 1)
        whenever(wordsRepository.getQuizWord(any(), any())).thenReturn(QUIZ_WORD_ITEM)
        quizViewModel.getQuestion()
        quizViewModel.onNextWordClick()
        quizViewModel.showFinishedDialog.getOrAwaitValue()
    }

    @Test
    fun `onNextWordClick() fetches next word`() = runBlockingTest {
        val quizViewModel = initViewModel()
        whenever(wordsRepository.getQuizWord(any(), any())).thenReturn(QUIZ_WORD_ITEM)
        quizViewModel.getQuestion()
        verify(wordsRepository, atLeastOnce()).getQuizWord(any(), any())
    }
}