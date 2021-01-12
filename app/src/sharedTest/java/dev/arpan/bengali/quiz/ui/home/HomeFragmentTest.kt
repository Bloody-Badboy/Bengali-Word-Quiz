package dev.arpan.bengali.quiz.ui.home

import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import dev.arpan.bengali.quiz.R
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config
import org.robolectric.annotation.LooperMode

//import org.robolectric.shadows.ShadowViewConfiguration

@RunWith(AndroidJUnit4::class)
@LooperMode(LooperMode.Mode.PAUSED)
@Config(qualifiers = "w480dp-h800dp")
class HomeFragmentTest {

    private lateinit var homeScenario: FragmentScenario<HomeFragment>
    private lateinit var navController: NavController

    private fun setupFragment() {
        navController = TestNavHostController(ApplicationProvider.getApplicationContext())
        navController.setGraph(R.navigation.nav_graph)

        homeScenario = launchFragmentInContainer<HomeFragment>(themeResId = R.style.AppTheme)
        homeScenario.onFragment { fragment ->
            Navigation.setViewNavController(fragment.requireView(), navController)
        }
    }

    @Test
    fun testNavigationToQuiz() {
        setupFragment()

        onView(withId(R.id.btn_bn_to_en)).perform(click())
        val destination = requireNotNull(navController.currentDestination)
        assertThat(destination.id).isEqualTo(R.id.nav_quiz)
    }

    @Test
    fun testNavigationToBookmark() {
        setupFragment()

        onView(withId(R.id.home_menu_bookmarked_words)).perform(click())
        val destination = requireNotNull(navController.currentDestination)
        assertThat(destination.id).isEqualTo(R.id.nav_bookmarked_words)
    }

    @Test
    fun testNavigationToChooseTheme() {
        setupFragment()

        // ShadowViewConfiguration.setHasPermanentMenuKey(false)

        openActionBarOverflowOrOptionsMenu(ApplicationProvider.getApplicationContext())

        onView(withText(R.string.home_menu_choose_theme)).perform(click())

        val destination = requireNotNull(navController.currentDestination)
        assertThat(destination.id).isEqualTo(R.id.nav_theme_dialog)
    }
}