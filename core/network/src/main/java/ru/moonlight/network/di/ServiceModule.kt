package ru.moonlight.network.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.moonlight.network.api.AuthApi
import ru.moonlight.network.api.ProfileApi
import ru.moonlight.network.service.AuthApiService
import ru.moonlight.network.service.AuthService
import ru.moonlight.network.service.ProfileApiService
import ru.moonlight.network.service.ProfileService
import ru.moonlight.network.utils.SessionManager
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {

    @Provides
    @Singleton
    internal fun providesAuthService(
        api: AuthApi,
        sessionManager: SessionManager,
    ): AuthService = AuthApiService(api, sessionManager)

    @Provides
    @Singleton
    internal fun providesProfileService(
        api: ProfileApi,
    ): ProfileService = ProfileApiService(api = api)

}