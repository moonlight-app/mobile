package ru.moonlight.feature_cart.impl.presentation

sealed class CartAction {
    class LoadCart : CartAction()
    data class ChangeChoseStatus(val itemId: Long, val status: Boolean) : CartAction()
    data class ChangeChoseStatusForAllItems(val status: Boolean) : CartAction()
    data class DeleteItemFromCart(val itemId: Long) : CartAction()
    data class IncreaseProductCount(val itemId: Long, val currentCount: Int) : CartAction()
    data class DecreaseProductCount(val itemId: Long, val currentCount: Int) : CartAction()
    class CompleteOrder : CartAction()
    class ClearUiState : CartAction()
    data class ChangeProductFavoriteStatus(val id: Long, val status: Boolean) : CartAction()
}