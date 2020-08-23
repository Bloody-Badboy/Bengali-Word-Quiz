package dev.arpan.bengali.quiz.ui.theme

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import dev.arpan.bengali.quiz.prefs.PreferenceStorage
import dev.arpan.bengali.quiz.prefs.Theme
import dev.arpan.bengali.quiz.prefs.themeFromStorageKey

class ThemeSettingDialogViewModel @ViewModelInject constructor(private val preferenceStorage: PreferenceStorage) :
    ViewModel() {

    private val _theme = MediatorLiveData<Theme>()
    val theme: LiveData<Theme>
        get() = _theme

    init {
        _theme.value =
            themeFromStorageKey(preferenceStorage.selectedTheme ?: Theme.default().storageKey)
        _theme.addSource(preferenceStorage.selectedThemeObservable) {
            _theme.value = themeFromStorageKey(it)
        }
    }

    fun setTheme(theme: Theme) {
        preferenceStorage.selectedTheme = theme.storageKey
    }
}
