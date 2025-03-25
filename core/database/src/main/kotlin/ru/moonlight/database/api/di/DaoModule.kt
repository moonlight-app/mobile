package ru.moonlight.database.api.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.moonlight.database.impl.MoonlightDatabase
import ru.moonlight.database.impl.catalog.ProductDao

@Module
@InstallIn(SingletonComponent::class)
internal object DaosModule {

    @Provides
    fun providesProductDao(
        database: MoonlightDatabase,
    ): ProductDao = database.productDao

}