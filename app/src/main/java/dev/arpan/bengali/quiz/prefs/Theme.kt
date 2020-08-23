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
