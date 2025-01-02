package ru.moonlight.feature_auth_signup_confirmcode.impl.presentation

import dagger.hilt.android.lifecycle.HiltViewModel
import ru.moonlight.common.ApiResponse
import ru.moonlight.common.base.BaseUIState
import ru.moonlight.common.base.BaseViewModel
import ru.moonlight.data.repository.AuthRepository
import javax.inject.Inject

@HiltViewModel
internal class ConfirmCodeViewModel @Inject constructor(
    private val authRepository: AuthRepository,
): BaseViewModel<ConfirmCodeState, ConfirmCodeSideEffect>(ConfirmCodeState()) {
    val uiState = baseUiState

    fun confirmCode(
        code: String,
        email: String,
        password: String,
        name: String,
        birthDate: String,
        sex: String,
    ) = intent {
        updateUiState(uiState = BaseUIState.Loading)
        val result = authRepository.confirmCode(
            code = code,
            email = email,
            password = password,
            name = name,
            birthDate = birthDate,
            sex = sex
        )
        when (result) {
            is ApiResponse.Error -> updateUiState(BaseUIState.Error(msg = result.msg))
            is ApiResponse.Success -> {
                updateUiState(uiState = BaseUIState.Success)
                postSideEffect(sideEffect = ConfirmCodeSideEffect.NavigateToLanding)
            }
        }
    }

    fun updateCode(code: String) = intent { reduce { state.copy(code = code) } }
}