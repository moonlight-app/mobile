package ru.moonlight.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import ru.moonlight.network.datasource.AuthDataSource
import javax.inject.Inject

private const val AUTH_PREFERENCES = "auth_tokens"
internal val Context.accountDataStore by preferencesDataStore(name = AUTH_PREFERENCES)

internal class AuthLocalDataSource @Inject constructor (
    private val dataStore: DataStore<Preferences>,
    private val dispatcherIO: CoroutineDispatcher,
): AuthDataSource {

    companion object {
        private val ACCESS_TOKEN_KEY = stringPreferencesKey("access_token")
        private val ACCESS_TOKEN_EXPIRES_IN_KEY = longPreferencesKey("access_token_expires_in")
        private val REFRESH_TOKEN_KEY = stringPreferencesKey("refresh_token")
        private val REFRESH_TOKEN_EXPIRES_IN_KEY = longPreferencesKey("refresh_token_expires_in")
    }

    override val isUserAuthorizedFlow: Flow<Boolean> = dataStore.data
        .map { preferences ->
            withContext(dispatcherIO) {
                preferences[ACCESS_TOKEN_KEY]?.isNotBlank() ?: false
            }
        }

    override val accessTokenFlow: Flow<String> = dataStore.data
        .map { preferences ->
            withContext(dispatcherIO) {
                preferences[ACCESS_TOKEN_KEY] ?: ""
            }
        }

    override val accessTokenExpiresInFlow: Flow<Long> = dataStore.data
        .map { preferences ->
            withContext(dispatcherIO) {
                preferences[ACCESS_TOKEN_EXPIRES_IN_KEY] ?: 0L
            }
        }


    override val refreshTokenFlow: Flow<String> = dataStore.data
        .map { preferences ->
            withContext(dispatcherIO) {
                preferences[REFRESH_TOKEN_KEY] ?: ""
            }
        }

    override val refreshTokenExpiresInFlow: Flow<Long> = dataStore.data
        .map { preferences ->
            withContext(dispatcherIO) {
                preferences[REFRESH_TOKEN_EXPIRES_IN_KEY] ?: 0L
            }
        }

    override suspend fun saveAccessToken(token: String) {
        withContext(Dispatchers.IO) {
            dataStore.edit { preferences ->
                preferences[ACCESS_TOKEN_KEY] = token
            }
        }
    }

    override suspend fun saveAccessTokenExpiresIn(time: Long) {
        withContext(dispatcherIO) {
            dataStore.edit { preferences ->
                preferences[ACCESS_TOKEN_EXPIRES_IN_KEY] = time
            }
        }
    }

    override suspend fun saveRefreshToken(token: String) {
        withContext(dispatcherIO) {
            dataStore.edit { preferences ->
                preferences[REFRESH_TOKEN_KEY] = token
            }
        }
    }

    override suspend fun saveRefreshTokenExpiresIn(time: Long) {
        withContext(dispatcherIO) {
            dataStore.edit { preferences ->
                preferences[REFRESH_TOKEN_EXPIRES_IN_KEY] = time
            }
        }
    }

    override suspend fun clearAccessToken() {
        withContext(dispatcherIO) {
            dataStore.edit { preferences ->
                preferences[ACCESS_TOKEN_KEY] = ""
            }
        }
    }

    override suspend fun clearRefreshToken() {
        withContext(dispatcherIO) {
            dataStore.edit { preferences ->
                preferences[REFRESH_TOKEN_KEY] = ""
            }
        }
    }

}