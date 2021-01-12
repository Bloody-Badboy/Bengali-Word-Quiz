package dev.arpan.bengali.quiz.testing

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HiltFragmentTestActivity : FragmentActivity() {
    companion object {
        const val THEME_EXTRAS_BUNDLE_KEY =
            "dev.arpan.bengali.quiz.test.HiltFragmentTestActivity.THEME_EXTRAS_BUNDLE_KEY"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(
            intent.getIntExtra(
                THEME_EXTRAS_BUNDLE_KEY,
                R.style.HilFragmentTestActivityTheme
            )
        )
        super.onCreate(savedInstanceState)
    }
}