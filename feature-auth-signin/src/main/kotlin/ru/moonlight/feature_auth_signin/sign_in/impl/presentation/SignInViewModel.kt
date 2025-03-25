package ru.moonlight.feature_auth_signin.sign_in.impl.presentation

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.moonlight.common.ApiResponse
import ru.moonlight.common.base.BaseUIState
import ru.moonlight.common.base.BaseViewModel
import ru.moonlight.data.api.repository.AuthRepository
import javax.inject.Inject

@HiltViewModel
internal class SignInViewModel @Inject constructor(
    private val authRepository: AuthRepository,
): BaseViewModel<SignInState, SignInSideEffect>(SignInState()) {
    val uiState = baseUiState

    fun dispatch(action: SignInAction) {
        when (action) {
            SignInAction.SignInClick -> login()
            SignInAction.RegistrationClick -> signUp()
            is SignInAction.UpdateLogin -> updateLogin(action.login)
            is SignInAction.UpdatePassword -> updatePassword(action.password)
        }
    }

    private fun login() {
        viewModelScope.launch {
            intent {
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
        }
    }

    private fun signUp() {
        viewModelScope.launch {
            intent { postSideEffect(sideEffect = SignInSideEffect.NavigateToSignUp) }
        }
    }

    private fun updateLogin(login: String) {
        viewModelScope.launch {
            intent {
                if (uiState.value is BaseUIState.Error) updateUiState(BaseUIState.Idle)
                reduce { state.copy(email = login) }
            }

        }
    }

    private fun updatePassword(password: String) {
        viewModelScope.launch {
            intent {
                if (uiState.value is BaseUIState.Error) updateUiState(BaseUIState.Idle)
                reduce { state.copy(password = password) }
            }
        }
    }

}