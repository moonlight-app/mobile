package ru.moonlight.common.di

import android.util.Log
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class CoroutinesScopeModule {

    @Singleton
    @Provides
    @CoroutineScopeAnnotation(MoonlightScope.ApplicationScope)
    fun providesCoroutineScope(@Dispatcher(MoonlightDispatchers.Default) defaultDispatcher: CoroutineDispatcher): CoroutineScope =
        CoroutineScope(SupervisorJob() + defaultDispatcher + CoroutineExceptionHandler { _, throwable ->
            Log.e("TAG", "application coroutine scope exception: ${throwable.message}", )
        })
}