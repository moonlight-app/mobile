package ru.moonlight.common.di

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class Dispatcher(val dispatcher: MoonlightDispatchers)

enum class MoonlightDispatchers {
    Default,
    IO,
    Main,
    Unconfined,
}