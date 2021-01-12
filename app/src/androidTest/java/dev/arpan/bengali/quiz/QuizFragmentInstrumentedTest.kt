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

package dev.arpan.bengali.quiz

import android.content.ComponentName
import android.content.Intent
import androidx.core.os.bundleOf
import androidx.test.core.app.ApplicationProvider
import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withParent
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import dev.arpan.bengali.quiz.testing.HiltFragmentTestActivity
import dev.arpan.bengali.quiz.ui.quiz.QuestionOptionsAdapter
import dev.arpan.bengali.quiz.ui.quiz.QuizFragment
import org.hamcrest.Matchers.allOf
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class QuizFragmentInstrumentedTest {

    companion object {
        private const val NUMBER_OF_WORDS = 10
        private const val NUMBER_OF_OPTIONS = 4
    }

    private val startActivityIntent: Intent = Intent.makeMainActivity(
        ComponentName(
            ApplicationProvider.getApplicationContext(),
            HiltFragmentTestActivity::class.java
        )
    ).putExtra(HiltFragmentTestActivity.THEME_EXTRAS_BUNDLE_KEY, R.style.AppTheme)

    private fun launchFragment() {
        val activityScenario = launchActivity<HiltFragmentTestActivity>(startActivityIntent)
        activityScenario.onActivity {
            val fragment = QuizFragment()
            fragment.arguments = bundleOf(
                "englishToBengali" to true,
                "numberOfWords" to NUMBER_OF_WORDS,
                "numberOfOptions" to NUMBER_OF_OPTIONS
            )
            it.supportFragmentManager.beginTransaction()
                .add(android.R.id.content, fragment, "TestFragment")
                .commitNow()
        }
    }

    private fun clickOnFirstOption() {
        onView(withId(R.id.recyclerView)).perform(
            RecyclerViewActions.actionOnItemAtPosition<QuestionOptionsAdapter.SingleChoiceItemViewHolder>(
                0,
                click()
            )
        )
    }

    private fun checkToolbarTitle(title: String) {
        onView(
            allOf(
                withText(title),
                withParent(withId(R.id.toolbar))
            )
        ).check(matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun toolbar_title_updates() {
        launchFragment()

        val context = ApplicationProvider.getApplicationContext<WordQuizApplication>()

        checkToolbarTitle(
            context.getString(R.string.quiz_toolbar_title, 1, NUMBER_OF_WORDS)
        )
        clickOnFirstOption()
        onView(withId(R.id.btn_next)).perform(click())
        checkToolbarTitle(
            context.getString(R.string.quiz_toolbar_title, 2, NUMBER_OF_WORDS)
        )
    }

    @Test
    fun next_button_only_after_selecting_option() {
        launchFragment()
        onView(withId(R.id.btn_next)).check(matches(ViewMatchers.isNotEnabled()))
        clickOnFirstOption()
        onView(withId(R.id.btn_next)).check(matches(ViewMatchers.isEnabled()))
    }

    @Test
    fun dialog_shoes_when_quiz_ends() {
        launchFragment()
        var count = 0
        do {
            clickOnFirstOption()
            onView(withId(R.id.btn_next)).perform(click())
            count++
        } while (count < NUMBER_OF_WORDS)
    }
}
