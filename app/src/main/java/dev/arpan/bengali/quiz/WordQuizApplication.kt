package dev.arpan.bengali.quiz

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class WordQuizApplication : Application() {
    companion object {
        lateinit var instance: WordQuizApplication
    }

    override fun onCreate() {
        super.onCreate()
        instance = this

        if (BuildConfig.DEBUG) {
            plantDebugTree()
        }
    }

    private fun plantDebugTree() {
        Timber.plant(object : Timber.DebugTree() {
            override fun createStackElementTag(element: StackTraceElement): String? {
                return ("WordQuiz"
                    + super.createStackElementTag(element)
                    + ":"
                    + element.lineNumber)
            }
        })
    }
}