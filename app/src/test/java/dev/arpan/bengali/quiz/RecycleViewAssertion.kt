package dev.arpan.bengali.quiz

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher

fun hasItemCount(count: Int): Matcher<View> {
    return object : TypeSafeMatcher<View>() {
        override fun describeTo(description: Description) {
            description.appendText("has item count: ").appendValue(count)
        }

        override fun matchesSafely(view: View): Boolean {
            if (view !is RecyclerView) {
                return false
            }
            return (view.adapter?.itemCount ?: 0) == count
        }
    }
}