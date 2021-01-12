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

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import dev.arpan.bengali.quiz.MainCoroutineRule
import dev.arpan.bengali.quiz.getOrAwaitValue
import dev.arpan.bengali.quiz.prefs.PreferenceStorage
import dev.arpan.bengali.quiz.prefs.Theme
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class ThemeSettingDialogViewModelTest {
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val selectedThemeLiveData = MutableLiveData<String>()
    private val preferenceStorage: PreferenceStorage = mock {
        whenever(mock.selectedTheme).thenReturn(Theme.DARK.storageKey)
        whenever(mock.selectedThemeObservable).thenReturn(selectedThemeLiveData)
    }

    @Test
    fun `uses default theme when prefs returns null`() {
        whenever(preferenceStorage.selectedTheme).thenReturn(null)
        val viewModel = ThemeSettingDialogViewModel(preferenceStorage)
        assertThat(viewModel.theme.getOrAwaitValue()).isEqualTo(Theme.default())
    }

    @Test
    fun `theme emits prefs returned value`() {
        val viewModel = ThemeSettingDialogViewModel(preferenceStorage)
        assertThat(viewModel.theme.getOrAwaitValue()).isEqualTo(Theme.DARK)
    }

    @Test
    fun `theme emits new value when theme observable changes`() {
        val viewModel = ThemeSettingDialogViewModel(preferenceStorage)
        assertThat(viewModel.theme.getOrAwaitValue()).isEqualTo(Theme.DARK)
        selectedThemeLiveData.value = Theme.LIGHT.storageKey
        assertThat(viewModel.theme.getOrAwaitValue()).isEqualTo(Theme.LIGHT)
    }
}
