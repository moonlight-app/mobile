package ru.moonlight.feature_cart.impl.presentation

data class CartState(
    val listOfChosenProduct: List<Long> = emptyList(),
)

sealed class CartUIState {
    class ChooseItems : CartUIState()
    class OrderCompleted : CartUIState()
}