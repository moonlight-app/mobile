package ru.moonlight.feature_profile_changepassword.impl.presentation

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.moonlight.common.ApiResponse
import ru.moonlight.common.base.BaseUIState
import ru.moonlight.common.base.BaseViewModel
import ru.moonlight.domain.profile.interactor.ChangePasswordInteractor
import javax.inject.Inject

@HiltViewModel
class ChangePasswordViewModel @Inject constructor(
    private val changePasswordInteractor: ChangePasswordInteractor,
): BaseViewModel<ChangePasswordState, ChangePasswordSideEffect>(ChangePasswordState()) {

    val uiState = baseUiState

    fun dispatch(action: ChangePasswordAction) {
        when (action) {
            is ChangePasswordAction.ChangePasswordClick -> changePassword()
            is ChangePasswordAction.NewPasswordChange -> updateNewPassword(action.password)
            is ChangePasswordAction.OldPasswordChange -> updateOldPassword(action.password)
        }
    }

    private fun changePassword() {
        updateUiState(BaseUIState.Loading)
        viewModelScope.launch {
            intent {
                when (val response = changePasswordInteractor(oldPassword = state.oldPassword, newPassword = state.newPassword)) {
                    is ApiResponse.Error -> updateUiState(BaseUIState.Error(response.msg))
                    is ApiResponse.Success -> {
                        intent {
                            postSideEffect(ChangePasswordSideEffect.ShowToast())
                            postSideEffect(ChangePasswordSideEffect.NavigateBack())
                        }
                        updateUiState(BaseUIState.Success)
                    }
                }
            }
        }
    }

    private fun updateOldPassword(oldPassword: String) {
        viewModelScope.launch {
            intent { reduce { state.copy(oldPassword = oldPassword) } }
        }
    }

    private fun updateNewPassword(newPassword: String) {
        viewModelScope.launch {
            intent { reduce { state.copy(newPassword = newPassword) } }
        }
    }

}