package ru.moonlight.common.base

import androidx.lifecycle.ViewModel
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container

open class BaseViewModel<State: Any, SideEffects: Any>(
    private val initialState: State,
) : ViewModel(), ContainerHost<State, SideEffects> {

    override val container = container<State, SideEffects>(initialState)
}