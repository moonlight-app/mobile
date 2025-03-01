package ru.moonlight.feature_catalog_product.impl.presentation

sealed class ProductDetailsSideEffect {
    class NavigateToCart : ProductDetailsSideEffect()
    class NavigateToAuthorize : ProductDetailsSideEffect()
}