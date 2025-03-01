package ru.moonlight.feature_auth_signup_registration.registration.api.navigation.presentation

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.moonlight.common.ApiResponse
import ru.moonlight.common.GenderOption
import ru.moonlight.common.base.BaseUIState
import ru.moonlight.common.base.BaseViewModel
import ru.moonlight.data.repository.AuthRepository
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val authRepository: AuthRepository,
): BaseViewModel<RegistrationState, RegistrationSideEffect>(RegistrationState()) {
    val uiState = baseUiState

    fun dispatch(action: RegistrationAction) {
        when (action) {
            is RegistrationAction.RequestCode -> requestCode()
            is RegistrationAction.ReRequestCode -> requestCode(renew = true)
            is RegistrationAction.ConfirmCode -> confirmCode()
            is RegistrationAction.UpdateName -> updateName(action.name)
            is RegistrationAction.UpdateBirthDate -> updateBirthDate(action.birthDate)
            is RegistrationAction.UpdateSex -> updateSex(action.sex)
            is RegistrationAction.UpdateEmail -> updateEmail(action.email)
            is RegistrationAction.UpdatePassword -> updatePassword(action.password)
            is RegistrationAction.UpdateCode -> updateCode(action.code)
        }
    }

    private fun requestCode(renew: Boolean? = null) = intent {
        viewModelScope.launch {
            updateUiState(BaseUIState.Loading)
            if (
                state.name.isEmpty() || state.email.isEmpty() ||
                state.birthDate.isEmpty() || state.sex == null ||
                state.password.isEmpty()
            ) {
                updateUiState(BaseUIState.Error("Заполните все поля"))
                return@launch
            }

            when (val result = authRepository.requestCode(email = state.email, name = state.name, renew = renew)) {
                is ApiResponse.Error -> updateUiState(BaseUIState.Error(result.msg))
                is ApiResponse.Success -> {
                    updateUiState(BaseUIState.Success)
                    postSideEffect(sideEffect = RegistrationSideEffect.OnCodeConfirmed)
                }
            }
        }
    }

    private fun confirmCode() = intent {
        viewModelScope.launch {
            updateUiState(uiState = BaseUIState.Loading)
            val result = authRepository.confirmCode(
                code = state.code,
                email = state.email,
                password = state.password,
                name = state.name,
                birthDate = state.birthDate,
                sex = state.sex?.name ?: throw IllegalStateException("gender is null"),
            )
            when (result) {
                is ApiResponse.Error -> updateUiState(BaseUIState.Error(msg = result.msg))
                is ApiResponse.Success -> {
                    updateUiState(uiState = BaseUIState.Success)
                    postSideEffect(sideEffect = RegistrationSideEffect.NavigateToLanding)
                }
            }
        }
    }

    private fun updateName(name: String) = intent {
        viewModelScope.launch {
            if (isErrorUiState) updateUiState(BaseUIState.Idle)
            reduce { state.copy(name = name) }
        }
    }

    private fun updateBirthDate(birthDate: String) = intent {
        viewModelScope.launch {
            if (isErrorUiState) updateUiState(BaseUIState.Idle)
            reduce { state.copy(birthDate = birthDate) }
        }
    }

    private fun updateSex(sex: GenderOption?) = intent {
        viewModelScope.launch {
            if (isErrorUiState) updateUiState(BaseUIState.Idle)
            reduce { state.copy(sex = sex) }
        }
    }

    private fun updateEmail(email: String) = intent {
        viewModelScope.launch {
            if (isErrorUiState) updateUiState(BaseUIState.Idle)
            reduce { state.copy(email = email) }
        }
    }

    private fun updatePassword(password: String) = intent {
        viewModelScope.launch {
            if (isErrorUiState) updateUiState(BaseUIState.Idle)
            reduce { state.copy(password = password) }
        }
    }

    private fun updateCode(code: String) = intent {
        viewModelScope.launch {
            reduce { state.copy(code = code) }
        }
    }

    private val isErrorUiState get() = uiState.value is BaseUIState.Error
}

