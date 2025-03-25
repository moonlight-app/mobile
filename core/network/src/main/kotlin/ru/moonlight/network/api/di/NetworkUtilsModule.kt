package ru.moonlight.network.api.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.moonlight.network.api.datasource.AuthDataSource
import ru.moonlight.network.impl.interceptor.AuthInterceptor
import ru.moonlight.network.impl.interceptor.UpdateTokenInterceptor
import ru.moonlight.network.impl.utils.ApiCallController
import ru.moonlight.network.impl.utils.ConnectivityManagerNetworkMonitor
import ru.moonlight.network.impl.utils.NetworkMonitor
import ru.moonlight.network.impl.utils.SessionManager
import ru.moonlight.network.impl.utils.SessionManagerImpl
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