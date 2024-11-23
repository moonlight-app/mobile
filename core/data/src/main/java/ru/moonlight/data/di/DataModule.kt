package ru.moonlight.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import ru.moonlight.common.di.Dispatcher
import ru.moonlight.common.di.MoonlightDispatchers
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
        @Dispatcher(MoonlightDispatchers.IO) dispatcher: CoroutineDispatcher,
    ): AuthRepository = AuthRepositoryImpl(service = authService, dispatcherIO = dispatcher)

    @Provides
    @Singleton
    fun providesProfileRepository(
        profileService: ProfileService,
        @Dispatcher(MoonlightDispatchers.IO) dispatcher: CoroutineDispatcher,
    ): ProfileRepository = ProfileRepositoryImpl(service = profileService, dispatcherIO = dispatcher)

}