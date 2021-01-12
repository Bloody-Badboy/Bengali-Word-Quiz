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
