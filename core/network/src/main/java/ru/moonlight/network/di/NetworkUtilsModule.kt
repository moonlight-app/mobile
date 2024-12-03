package ru.moonlight.network.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.moonlight.network.datasource.AuthDataSource
import ru.moonlight.network.interceptor.AuthInterceptor
import ru.moonlight.network.interceptor.UpdateTokenInterceptor
import ru.moonlight.network.utils.ApiCallController
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
    internal fun providesAuthInterceptor(
        sessionManager: SessionManager
    ): AuthInterceptor = AuthInterceptor(sessionManager = sessionManager)

    @Provides
    @Singleton
    internal fun providesUpdateTokenInterceptor(
        sessionManager: SessionManager
    ): UpdateTokenInterceptor = UpdateTokenInterceptor(sessionManager = sessionManager)

    @Provides
    @Singleton
    internal fun providesApiCallController(
        networkMonitor: NetworkMonitor,
    ): ApiCallController = ApiCallController(networkMonitor = networkMonitor)

}