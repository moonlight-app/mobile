package ru.moonlight.network.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.moonlight.network.service.AuthService
import ru.moonlight.network.utils.AuthDataSource
import ru.moonlight.network.utils.TokenManager
import ru.moonlight.network.utils.TokenManagerImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun providesAuthService(
        tokenManager: TokenManager
    ): AuthService = AuthService(tokenManager = tokenManager)

    @Singleton
    @Provides
    fun providesTokenManager(
        authDataSource: AuthDataSource
    ): TokenManager = TokenManagerImpl(authDataSource = authDataSource)

}