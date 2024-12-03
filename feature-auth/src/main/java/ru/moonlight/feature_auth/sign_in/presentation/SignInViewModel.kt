package ru.moonlight.feature_auth.sign_in.presentation

import dagger.hilt.android.lifecycle.HiltViewModel
import ru.moonlight.common.base.BaseUIState
import ru.moonlight.common.base.BaseViewModel
import ru.moonlight.data.repository.AuthRepository
import ru.moonlight.network.utils.ApiResponse
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val authRepository: AuthRepository,
): BaseViewModel<SignInState, SignInSideEffect>(SignInState()) {
    val uiState = baseUiState

    fun login() = intent {
        updateUiState(uiState = BaseUIState.Loading)
        if (state.email.isEmpty() || state.password.isEmpty()) {
            updateUiState(BaseUIState.Error("Заполните все поля"))
            return@intent
        }
        when (val result = authRepository.login(state.email, state.password)) {
            is ApiResponse.Error -> updateUiState(BaseUIState.Error(result.msg))
            is ApiResponse.Success -> {
                postSideEffect(sideEffect = SignInSideEffect.NavigateToProfile)
            }
        }
    }

    fun signUp() = intent {
        postSideEffect(sideEffect = SignInSideEffect.NavigateToSignUp)
    }

    fun updateLogin(login: String) = intent {
        if (uiState.value is BaseUIState.Error) updateUiState(BaseUIState.Idle)
        reduce { state.copy(email = login) }
    }

    fun updatePassword(password: String) = intent {
        if (uiState.value is BaseUIState.Error) updateUiState(BaseUIState.Idle)
        reduce { state.copy(password = password) }
    }

}