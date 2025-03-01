package ru.moonlight.feature_catalog.impl.presentation

internal sealed class CatalogSideEffect {
    data object NavigateBack : CatalogSideEffect()
    data class NavigateToProduct(val productId: Long) : CatalogSideEffect()
}