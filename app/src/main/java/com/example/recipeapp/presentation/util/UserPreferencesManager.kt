package com.example.recipeapp.presentation.util

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class UserPreferencesManager @Inject constructor(
    @ApplicationContext val context: Context
) {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = STORE_NAME)

    data class UserPreferences(val darkTheme: Boolean)

    private object PreferencesKeys {
        val SHOW_DARK_THEME = booleanPreferencesKey("show_dark_theme")
    }

    val userPreferencesFlow: Flow<UserPreferences> = context.dataStore.data
        .catch { exception ->
            // dataStore.data throws an IOException when an error is encountered when reading data
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            // Get our show completed value, defaulting to false if not set:
            val showCompleted = preferences[PreferencesKeys.SHOW_DARK_THEME] ?: false
            UserPreferences(showCompleted)
        }

    suspend fun updateShowDarkTheme(showDarkTheme: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.SHOW_DARK_THEME] = showDarkTheme
        }
    }
}