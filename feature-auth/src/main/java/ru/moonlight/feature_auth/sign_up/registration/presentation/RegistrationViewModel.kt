package ru.moonlight.feature_auth.sign_up.registration.presentation

import dagger.hilt.android.lifecycle.HiltViewModel
import ru.moonlight.common.GenderOption
import ru.moonlight.common.base.BaseUIState
import ru.moonlight.common.base.BaseViewModel
import ru.moonlight.data.repository.AuthRepository
import ru.moonlight.common.ApiResponse
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val authRepository: AuthRepository,
): BaseViewModel<RegistrationState, RegistrationSideEffect>(RegistrationState()) {
    val uiState = baseUiState

    fun requestCode() = intent {
        updateUiState(BaseUIState.Loading)
        if (
            state.name.isEmpty() || state.email.isEmpty() ||
            state.birthDate.isEmpty() || state.sex == null ||
            state.password.isEmpty()
        ) {
            updateUiState(BaseUIState.Error("Заполните все поля"))
            return@intent
        }

        when (val result = authRepository.requestCode(email = state.email, name = state.name)) {
            is ApiResponse.Error -> updateUiState(BaseUIState.Error(result.msg))
            is ApiResponse.Success -> {
                updateUiState(BaseUIState.Success)
                postSideEffect(sideEffect = RegistrationSideEffect.OnCodeConfirmed)
            }
        }
    }

    fun updateName(name: String) = intent {
        if (isErrorUiState) updateUiState(BaseUIState.Idle)
        reduce { state.copy(name = name) }
    }

    fun updateBirthDate(birthDate: String) = intent {
        if (isErrorUiState) updateUiState(BaseUIState.Idle)
        reduce { state.copy(birthDate = birthDate) }
    }

    fun updateSex(sex: GenderOption?) = intent {
        if (isErrorUiState) updateUiState(BaseUIState.Idle)
        reduce { state.copy(sex = sex) }
    }

    fun updateEmail(email: String) = intent {
        if (isErrorUiState) updateUiState(BaseUIState.Idle)
        reduce { state.copy(email = email) }
    }

    fun updatePassword(password: String) = intent {
        if (isErrorUiState) updateUiState(BaseUIState.Idle)
        reduce { state.copy(password = password) }
    }

    private val isErrorUiState get() = uiState.value is BaseUIState.Error
}

