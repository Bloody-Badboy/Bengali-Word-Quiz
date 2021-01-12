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
