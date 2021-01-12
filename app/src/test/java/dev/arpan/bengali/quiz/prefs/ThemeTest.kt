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

package dev.arpan.bengali.quiz.prefs

import android.os.Build
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@RunWith(AndroidJUnit4::class)
class ThemeTest {

    @Test
    @Config(sdk = [Build.VERSION_CODES.Q])
    fun `default theme for sdk Q or higher`() {
        assertThat(Theme.default()).isEqualTo(Theme.SYSTEM)
    }

    @Test
    @Config(sdk = [Build.VERSION_CODES.Q])
    fun `available themes for sdk Q or higher`() {
        assertThat(Theme.availableThemes).isEqualTo(
            listOf(
                Theme.LIGHT,
                Theme.DARK,
                Theme.SYSTEM
            )
        )
    }

    @Test
    @Config(sdk = [Config.OLDEST_SDK])
    fun `default theme for sdk less than Q`() {
        assertThat(Theme.default()).isEqualTo(Theme.BATTERY_SAVER)
    }

    @Test
    @Config(sdk = [Config.OLDEST_SDK])
    fun `available themes for sdk less than Q`() {
        assertThat(Theme.availableThemes).isEqualTo(
            listOf(
                Theme.LIGHT,
                Theme.DARK,
                Theme.BATTERY_SAVER
            )
        )
    }

    @Test
    fun `storage key to theme`() {
        assertThat(themeFromStorageKey(Theme.SYSTEM.storageKey)).isEqualTo(Theme.SYSTEM)
        assertThat(themeFromStorageKey(Theme.LIGHT.storageKey)).isEqualTo(Theme.LIGHT)
        assertThat(themeFromStorageKey(Theme.DARK.storageKey)).isEqualTo(Theme.DARK)
        assertThat(themeFromStorageKey(Theme.BATTERY_SAVER.storageKey)).isEqualTo(Theme.BATTERY_SAVER)
    }
}
