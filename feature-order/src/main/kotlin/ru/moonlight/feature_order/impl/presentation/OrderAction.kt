package ru.moonlight.feature_order.impl.presentation

sealed class OrderAction {
    class LoadData : OrderAction()
}