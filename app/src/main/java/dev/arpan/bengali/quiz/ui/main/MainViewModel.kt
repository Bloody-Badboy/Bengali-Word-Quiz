package dev.arpan.bengali.quiz.ui.main

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import dev.arpan.bengali.quiz.prefs.PreferenceStorage
import dev.arpan.bengali.quiz.prefs.Theme
import dev.arpan.bengali.quiz.prefs.themeFromStorageKey

class MainViewModel @ViewModelInject constructor(private val preferenceStorage: PreferenceStorage) :
    ViewModel() {
    val theme: LiveData<Theme> by lazy(LazyThreadSafetyMode.NONE) {
        preferenceStorage.selectedThemeObservable.map {
            themeFromStorageKey(it)
        }
    }

    val currentTheme: Theme
        get() {
            preferenceStorage.selectedTheme?.let {
                return themeFromStorageKey(it)
            }

            return Theme.default()
        }
}