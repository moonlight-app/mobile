package ru.moonlight.database.api.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ru.moonlight.database.impl.MoonlightDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object DatabaseModule {

    @Provides
    @Singleton
    fun providesMoonlightDatabase(
        @ApplicationContext context: Context,
    ): MoonlightDatabase = Room.databaseBuilder(
        context,
        MoonlightDatabase::class.java,
        "moonlight-database",
    ).build()

}