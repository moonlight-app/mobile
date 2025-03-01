package ru.moonlight.network.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.moonlight.network.api.AuthApi
import ru.moonlight.network.api.CartApi
import ru.moonlight.network.api.CatalogApi
import ru.moonlight.network.api.FavoritesApi
import ru.moonlight.network.api.OrderApi
import ru.moonlight.network.api.ProfileApi
import ru.moonlight.network.service.AuthApiService
import ru.moonlight.network.service.AuthService
import ru.moonlight.network.service.CartApiService
import ru.moonlight.network.service.CartService
import ru.moonlight.network.service.CatalogApiService
import ru.moonlight.network.service.CatalogService
import ru.moonlight.network.service.FavoritesApiService
import ru.moonlight.network.service.FavoritesService
import ru.moonlight.network.service.OrderApiService
import ru.moonlight.network.service.OrderService
import ru.moonlight.network.service.ProfileApiService
import ru.moonlight.network.service.ProfileService
import ru.moonlight.network.utils.ApiCallController
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
        apiCallController: ApiCallController,
    ): AuthService = AuthApiService(api = api, sessionManager = sessionManager, apiCallController = apiCallController)

    @Provides
    @Singleton
    internal fun providesProfileService(
        api: ProfileApi,
        apiCallController: ApiCallController,
    ): ProfileService = ProfileApiService(api = api, apiCallController = apiCallController)

    @Provides
    @Singleton
    internal fun providesCatalogService(
        api: CatalogApi,
        apiCallController: ApiCallController,
    ): CatalogService = CatalogApiService(api = api, apiCallController = apiCallController)

    @Provides
    @Singleton
    internal fun providesCartService(
        api: CartApi,
        apiCallController: ApiCallController,
    ): CartService = CartApiService(api = api, apiCallController = apiCallController)

    @Provides
    @Singleton
    internal fun providesOrderService(
        api: OrderApi,
        apiCallController: ApiCallController,
    ): OrderService = OrderApiService(api = api, apiCallController = apiCallController)

    @Provides
    @Singleton
    internal fun providesFavoritesService(
        api: FavoritesApi,
        apiCallController: ApiCallController,
    ): FavoritesService = FavoritesApiService(api = api, apiCallController = apiCallController)
}