package io.techmeskills.an02onl_plannerapp.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import io.techmeskills.an02onl_plannerapp.BuildConfig
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map


private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    "${BuildConfig.APPLICATION_ID}_datastore"
)

class AppSettings(context: Context) {

    private val dataStore = context.dataStore

    fun userNameFlow(): Flow<String> = dataStore.data.map { preferences ->
        preferences[stringPreferencesKey(USER_KEY)] ?: ""
    }

    suspend fun getUserName(): String = userNameFlow().first()

    suspend fun setUserName(userName: String) {
        dataStore.edit { preferences ->
            preferences[stringPreferencesKey(USER_KEY)] = userName
        }
    }

    companion object {
        private const val USER_KEY = "USER_KEY"
    }
}
