package ru.moonlight.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.moonlight.data.repository.AuthRepository
import ru.moonlight.data.repository.AuthRepositoryImpl
import ru.moonlight.network.service.AuthService
import ru.moonlight.network.utils.TokenManager
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Singleton
    @Provides
    fun providesAuthRepository(
        api: AuthService,
        tokenManager: TokenManager
    ): AuthRepository = AuthRepositoryImpl(api = api, tokenManager = tokenManager)

}