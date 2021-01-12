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

enum class Theme(val storageKey: String) {
    LIGHT("light"),
    DARK("dark"),
    SYSTEM("system"),
    BATTERY_SAVER("battery_saver");

    companion object {
        fun default(): Theme {
            return when {
                Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q -> SYSTEM
                else -> BATTERY_SAVER
            }
        }

        val availableThemes: List<Theme>
            get() {
                return when {
                    Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q -> {
                        listOf(LIGHT, DARK, SYSTEM)
                    }
                    else -> {
                        listOf(LIGHT, DARK, BATTERY_SAVER)
                    }
                }
            }
    }
}

fun themeFromStorageKey(storageKey: String): Theme {
    return Theme.values().first { it.storageKey == storageKey }
}
