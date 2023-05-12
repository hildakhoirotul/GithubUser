package com.example.submission1.database

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.prefDataStore by preferencesDataStore("settings")

class SettingPreferences constructor(context: Context) {
    private val settingDataStore = context.prefDataStore
    private val themeKey = booleanPreferencesKey("setting_theme")

    fun getSettingTheme(): Flow<Boolean> =
        settingDataStore.data.map {
            it[themeKey] ?: false
        }

    suspend fun saveSettingTheme(isDarkModeActive: Boolean) {
        settingDataStore.edit {
            it[themeKey] = isDarkModeActive
        }
    }
}