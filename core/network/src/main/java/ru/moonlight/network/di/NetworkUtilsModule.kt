package ru.moonlight.network.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.moonlight.network.datasource.AuthDataSource
import ru.moonlight.network.interceptor.AuthInterceptor
import ru.moonlight.network.interceptor.TokenAuthenticator
import ru.moonlight.network.utils.ConnectivityManagerNetworkMonitor
import ru.moonlight.network.utils.NetworkMonitor
import ru.moonlight.network.utils.SessionManager
import ru.moonlight.network.utils.SessionManagerImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkUtilsModule {

    @Provides
    @Singleton
    internal fun providesNetworkMonitor(
        networkMonitor: ConnectivityManagerNetworkMonitor,
    ): NetworkMonitor = networkMonitor

    @Provides
    @Singleton
    internal fun providesSessionManager(
        authDataSource: AuthDataSource,
    ): SessionManager = SessionManagerImpl(authDataSource = authDataSource)

    @Provides
    @Singleton
    internal fun providesTokenAuthenticator(
        sessionManager: SessionManager
    ): TokenAuthenticator = TokenAuthenticator(sessionManager)

    @Provides
    @Singleton
    internal fun providesAuthInterceptor(
        sessionManager: SessionManager
    ): AuthInterceptor = AuthInterceptor(sessionManager)

}