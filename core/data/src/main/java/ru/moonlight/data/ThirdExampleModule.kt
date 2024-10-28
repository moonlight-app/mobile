package ru.moonlight.data

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ThirdExampleModule {

    @Provides
    @Singleton
    fun provideThirdExample(): ThirdExampleRepository = ThirdExampleRepositoryImpl()

}