package ru.moonlight.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import ru.moonlight.network.utils.AuthDataSource
import javax.inject.Inject

private const val AUTH_PREFERENCES = "auth_tokens"
internal val Context.accountDataStore by preferencesDataStore(name = AUTH_PREFERENCES)

internal class AuthLocalDataSource @Inject constructor (
    private val dataStore: DataStore<Preferences>,
): AuthDataSource {

    companion object {
        private val ACCESS_TOKEN_KEY = stringPreferencesKey("access_token")
        private val REFRESH_TOKEN_KEY = stringPreferencesKey("refresh_token")
    }

    override val isUserAuthorizedFlow: Flow<Boolean> = dataStore.data
        .map { preferences ->
            preferences[ACCESS_TOKEN_KEY]?.isNotBlank() ?: false
        }

    override val accessTokenFlow: Flow<String> = dataStore.data
        .map { preferences ->
            preferences[ACCESS_TOKEN_KEY] ?: ""
        }

    override val refreshTokenFlow: Flow<String> = dataStore.data
        .map { preferences ->
            preferences[REFRESH_TOKEN_KEY] ?: ""
        }

    override suspend fun saveAccessToken(token: String) {
        dataStore.edit { preferences ->
            preferences[ACCESS_TOKEN_KEY] = token
        }
    }

    override suspend fun saveRefreshToken(token: String) {
        dataStore.edit { preferences ->
            preferences[REFRESH_TOKEN_KEY] = token
        }
    }

    override suspend fun clearAccessToken() {
        dataStore.edit { preferences ->
            preferences[ACCESS_TOKEN_KEY] = ""
        }
    }

    override suspend fun clearRefreshToken() {
        dataStore.edit { preferences ->
            preferences[REFRESH_TOKEN_KEY] = ""
        }
    }

}