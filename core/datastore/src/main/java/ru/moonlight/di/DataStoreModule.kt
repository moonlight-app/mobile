package ru.moonlight.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import ru.moonlight.common.di.Dispatcher
import ru.moonlight.common.di.MoonlightDispatchers
import ru.moonlight.datastore.AuthLocalDataSource
import ru.moonlight.datastore.accountDataStore
import ru.moonlight.network.datasource.AuthDataSource
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {

    @Provides
    @Singleton
    internal fun providesAuthPreferencesDataStore(
        @ApplicationContext context: Context,
    ): DataStore<Preferences> = context.accountDataStore

    @Provides
    @Singleton
    fun providesAuthDataStore(
        dataStore: DataStore<Preferences>,
        @Dispatcher(MoonlightDispatchers.IO) dispatcher: CoroutineDispatcher,
    ): AuthDataSource = AuthLocalDataSource(dataStore = dataStore, dispatcherIO = dispatcher)

}