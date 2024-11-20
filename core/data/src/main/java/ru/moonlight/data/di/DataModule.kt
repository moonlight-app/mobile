package ru.moonlight.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.moonlight.data.repository.AuthRepository
import ru.moonlight.data.repository.AuthRepositoryImpl
import ru.moonlight.data.repository.ProfileRepository
import ru.moonlight.data.repository.ProfileRepositoryImpl
import ru.moonlight.network.service.AuthService
import ru.moonlight.network.service.ProfileService
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun providesAuthRepository(
        authService: AuthService,
    ): AuthRepository = AuthRepositoryImpl(service = authService)

    @Provides
    @Singleton
    fun providesProfileRepository(
        profileService: ProfileService,
    ): ProfileRepository = ProfileRepositoryImpl(service = profileService)

}