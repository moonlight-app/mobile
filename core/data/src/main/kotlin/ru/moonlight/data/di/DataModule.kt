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
import ru.moonlight.data.repository.CartRepository
import ru.moonlight.data.repository.CartRepositoryImpl
import ru.moonlight.data.repository.CatalogRepository
import ru.moonlight.data.repository.CatalogRepositoryImpl
import ru.moonlight.data.repository.FavoritesRepository
import ru.moonlight.data.repository.FavoritesRepositoryImpl
import ru.moonlight.data.repository.OrderRepository
import ru.moonlight.data.repository.OrderRepositoryImpl
import ru.moonlight.data.repository.ProfileRepository
import ru.moonlight.data.repository.ProfileRepositoryImpl
import ru.moonlight.database.MoonlightDatabase
import ru.moonlight.network.service.AuthService
import ru.moonlight.network.service.CartService
import ru.moonlight.network.service.CatalogService
import ru.moonlight.network.service.FavoritesService
import ru.moonlight.network.service.OrderService
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