package ru.moonlight.feature_catalog_product.impl.presentation

sealed class ProductDetailsAction {
    data class LoadDetails(val productId: Long) : ProductDetailsAction()
    data class UpdateChosenSize(val size: String) : ProductDetailsAction()
    class ChangeFavouritesStatus : ProductDetailsAction()
    class AddToCart : ProductDetailsAction()
    class GoToCartClick : ProductDetailsAction()
    class GoToAuthClick : ProductDetailsAction()
}