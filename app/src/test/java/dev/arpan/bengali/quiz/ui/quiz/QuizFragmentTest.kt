package dev.arpan.bengali.quiz.ui.quiz

import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.isEnabled
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withParent
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import dev.arpan.bengali.quiz.QUIZ_WORD_ITEM
import dev.arpan.bengali.quiz.R
import dev.arpan.bengali.quiz.WordQuizApplication
import dev.arpan.bengali.quiz.data.WordsRepository
import dev.arpan.bengali.quiz.hasItemCount
import kotlinx.coroutines.runBlocking
import org.hamcrest.Matchers.allOf
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config
import org.robolectric.annotation.LooperMode

@RunWith(AndroidJUnit4::class)
@LooperMode(LooperMode.Mode.PAUSED)
@Config(qualifiers = "w480dp-h800dp")
class QuizFragmentTest {

    companion object {
        private const val NUMBER_OF_WORDS = 10
        private const val NUMBER_OF_OPTIONS = 4
    }

    private val wordsRepository: WordsRepository = mock {
        runBlocking {
            whenever(mock.getQuizWord(any(), any())).thenReturn(QUIZ_WORD_ITEM)
        }
    }

    private val testViewModelFactory = object : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return QuizViewModel(wordsRepository) as T
        }
    }

    private val testFragmentFactory = object : FragmentFactory() {
        override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
            return when (loadFragmentClass(classLoader, className)) {
                QuizFragment::class.java -> QuizFragment(testViewModelFactory)
                else -> super.instantiate(classLoader, className)
            }
        }
    }

    private val fragmentArgs = bundleOf(
        "englishToBengali" to true,
        "numberOfWords" to NUMBER_OF_WORDS,
        "numberOfOptions" to NUMBER_OF_OPTIONS
    )

    private fun clickOnFirstOption() {
        onView(withId(R.id.recyclerView)).perform(
            RecyclerViewActions.actionOnItemAtPosition<QuestionOptionsAdapter.SingleChoiceItemViewHolder>(
                0,
                ViewActions.click()
            )
        )
    }

    private fun checkToolbarTitle(title: String) {
        onView(
            allOf(
                withText(title),
                withParent(withId(R.id.toolbar))
            )
        ).check(matches(isDisplayed()))
    }

    @Test
    fun `next word button only enables after selecting an option`() {
        launchFragmentInContainer<QuizFragment>(
            fragmentArgs = fragmentArgs,
            themeResId = R.style.AppTheme,
            factory = testFragmentFactory
        )

        onView(withId(R.id.btn_next)).check(matches(ViewMatchers.isNotEnabled()))
        onView(withId(R.id.recyclerView)).check(matches(hasItemCount(NUMBER_OF_OPTIONS)))
        clickOnFirstOption()
        onView(withId(R.id.btn_next)).check(matches(isEnabled()))
    }

    @Test
    fun `toolbar title updates correctly`() {
        launchFragmentInContainer<QuizFragment>(
            fragmentArgs = fragmentArgs,
            themeResId = R.style.AppTheme,
            factory = testFragmentFactory
        )

        val context = ApplicationProvider.getApplicationContext<WordQuizApplication>()

        checkToolbarTitle(
            context.getString(R.string.quiz_toolbar_title, 1, NUMBER_OF_WORDS)
        )
        clickOnFirstOption()
        onView(withId(R.id.btn_next)).perform(ViewActions.click())
        checkToolbarTitle(
            context.getString(R.string.quiz_toolbar_title, 2, NUMBER_OF_WORDS)
        )
    }
}