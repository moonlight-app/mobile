package ru.moonlight.network.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.moonlight.network.utils.ConnectivityManagerNetworkMonitor
import ru.moonlight.network.utils.NetworkMonitor

@Module
@InstallIn(SingletonComponent::class)
abstract class NetworkModule {

    @Binds
    internal abstract fun bindsNetworkMonitor(
        networkMonitor: ConnectivityManagerNetworkMonitor,
    ): NetworkMonitor

}