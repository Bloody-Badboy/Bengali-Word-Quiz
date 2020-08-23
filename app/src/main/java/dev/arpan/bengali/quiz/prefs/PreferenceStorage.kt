package dev.arpan.bengali.quiz.prefs

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

interface PreferenceStorage {
    var selectedTheme: String?

    val selectedThemeObservable: LiveData<String>
}

class SharedPreferenceStorage(
    private val context: Context,
) : PreferenceStorage {

    companion object {
        const val PREFS_NAME = "word_quiz"
        const val PREF_DARK_MODE_ENABLED = "pref_dark_mode"
    }

    private val prefs: Lazy<SharedPreferences> = lazy {
        context.applicationContext.getSharedPreferences(
            PREFS_NAME, Context.MODE_PRIVATE
        ).apply {
            registerOnSharedPreferenceChangeListener(changeListener)
        }
    }

    private val _observableSelectedThemeResult = MutableLiveData<String>()

    private val changeListener = SharedPreferences.OnSharedPreferenceChangeListener { _, key ->
        when (key) {
            PREF_DARK_MODE_ENABLED -> _observableSelectedThemeResult.value = selectedTheme
        }
    }

    override var selectedTheme: String? by StringPreference(
        prefs,
        PREF_DARK_MODE_ENABLED,
        Theme.SYSTEM.storageKey
    )

    override val selectedThemeObservable: LiveData<String>
        get() = _observableSelectedThemeResult
}

class StringPreference(
    private val preferences: Lazy<SharedPreferences>,
    private val name: String,
    private val defaultValue: String?
) : ReadWriteProperty<Any, String?> {

    override fun getValue(
        thisRef: Any,
        property: KProperty<*>
    ): String? {
        return preferences.value.getString(name, defaultValue)
    }

    override fun setValue(
        thisRef: Any,
        property: KProperty<*>,
        value: String?
    ) {
        preferences.value.edit { putString(name, value) }
    }
}

