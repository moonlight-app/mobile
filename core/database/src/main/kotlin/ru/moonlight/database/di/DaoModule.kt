package ru.moonlight.database.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.moonlight.database.MoonlightDatabase
import ru.moonlight.database.catalog.ProductDao

@Module
@InstallIn(SingletonComponent::class)
internal object DaosModule {

    @Provides
    fun providesTopicsDao(
        database: MoonlightDatabase,
    ): ProductDao = database.productDao

}