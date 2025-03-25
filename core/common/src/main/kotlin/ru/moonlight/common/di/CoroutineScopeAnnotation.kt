package ru.moonlight.common.di

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class CoroutineScopeAnnotation(val scope: MoonlightScope)

enum class MoonlightScope {
    ApplicationScope,
}
