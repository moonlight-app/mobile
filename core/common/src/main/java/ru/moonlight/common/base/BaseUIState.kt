package ru.moonlight.common.base

sealed class BaseUIState {
    data object Idle: BaseUIState()
    data object Loading: BaseUIState()
    data object Success : BaseUIState()
    data class Error(val msg: String?): BaseUIState()
}