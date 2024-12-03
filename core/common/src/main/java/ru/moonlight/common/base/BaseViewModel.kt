package ru.moonlight.common.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container

open class BaseViewModel<State: Any, SideEffects: Any>(
    private val initialState: State,
) : ViewModel(), ContainerHost<State, SideEffects> {

    override val container = container<State, SideEffects>(initialState)

    private var _baseUiState = MutableStateFlow<BaseUIState>(BaseUIState.Idle)
    val baseUiState = _baseUiState.asStateFlow()

    fun updateUiState(uiState: BaseUIState) {
        _baseUiState.value = uiState
    }

}