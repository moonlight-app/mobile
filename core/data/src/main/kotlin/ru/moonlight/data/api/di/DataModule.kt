package ru.moonlight.data.api.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import ru.moonlight.common.di.Dispatcher
import ru.moonlight.common.di.MoonlightDispatchers
import ru.moonlight.data.api.repository.AuthRepository
import ru.moonlight.data.api.repository.CartRepository
import ru.moonlight.data.api.repository.CatalogRepository
import ru.moonlight.data.api.repository.FavoritesRepository
import ru.moonlight.data.api.repository.OrderRepository
import ru.moonlight.data.api.repository.ProfileRepository
import ru.moonlight.data.impl.repository.AuthRepositoryImpl
import ru.moonlight.data.impl.repository.CartRepositoryImpl
import ru.moonlight.data.impl.repository.CatalogRepositoryImpl
import ru.moonlight.data.impl.repository.FavoritesRepositoryImpl
import ru.moonlight.data.impl.repository.OrderRepositoryImpl
import ru.moonlight.data.impl.repository.ProfileRepositoryImpl
import ru.moonlight.database.impl.MoonlightDatabase
import ru.moonlight.network.api.service.AuthService
import ru.moonlight.network.api.service.CartService
import ru.moonlight.network.api.service.CatalogService
import ru.moonlight.network.api.service.FavoritesService
import ru.moonlight.network.api.service.OrderService
import ru.moonlight.network.api.service.ProfileService
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

    @Provides
    @Singleton
    fun providesCatalogRepository(
        catalogService: CatalogService,
        database: MoonlightDatabase,
        @Dispatcher(MoonlightDispatchers.IO) dispatcher: CoroutineDispatcher,
    ): CatalogRepository = CatalogRepositoryImpl(service = catalogService, database = database, dispatcherIO = dispatcher)

    @Provides
    @Singleton
    fun providesCartRepository(
        catalogService: CartService,
        database: MoonlightDatabase,
        @Dispatcher(MoonlightDispatchers.IO) dispatcher: CoroutineDispatcher,
    ): CartRepository = CartRepositoryImpl(service = catalogService, database = database, dispatcherIO = dispatcher)

    @Provides
    @Singleton
    fun providesOrderRepository(
        orderService: OrderService,
        @Dispatcher(MoonlightDispatchers.IO) dispatcher: CoroutineDispatcher,
    ): OrderRepository = OrderRepositoryImpl(service = orderService, dispatcherIO = dispatcher)

    @Provides
    @Singleton
    fun providesFavoritesRepository(
        favoriteService: FavoritesService,
        database: MoonlightDatabase,
        @Dispatcher(MoonlightDispatchers.IO) dispatcher: CoroutineDispatcher,
    ): FavoritesRepository = FavoritesRepositoryImpl(service = favoriteService, database = database, dispatcherIO = dispatcher)

}