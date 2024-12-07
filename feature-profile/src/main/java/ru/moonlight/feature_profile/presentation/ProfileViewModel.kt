package ru.moonlight.feature_profile.presentation

import dagger.hilt.android.lifecycle.HiltViewModel
import ru.moonlight.common.base.BaseUIState
import ru.moonlight.common.base.BaseViewModel
import ru.moonlight.data.repository.AuthRepository
import ru.moonlight.data.repository.ProfileRepository
import ru.moonlight.network.utils.ApiResponse
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val profileRepository: ProfileRepository,
): BaseViewModel<ProfileState, ProfileSideEffects>(ProfileState()) {
    val uiState = baseUiState

    fun loadProfile() = intent {
        updateUiState(BaseUIState.Loading)

        when (val result = profileRepository.getProfile()) {
            is ApiResponse.Error -> updateUiState(BaseUIState.Error(result.msg))
            is ApiResponse.Success -> {
                reduce { state.copy(
                    email = result.data?.email!!,
                    name = result.data?.name!!,
                    sex = result.data?.sex!!,
                    birthDate = result.data?.birthDate!!
                )}
                updateUiState(BaseUIState.Success)
            }
        }
    }

    fun navigateToEditProfile() = intent { postSideEffect(ProfileSideEffects.NavigateToEditProfile) }

    fun navigateToOrders() = intent { postSideEffect(ProfileSideEffects.NavigateToOrders) }

    fun navigateToFavorites() = intent { postSideEffect(ProfileSideEffects.NavigateToFavorites) }

    fun showChangePasswordDialog() = intent { postSideEffect(ProfileSideEffects.ChangePassword) }

    fun updatePassword(oldPassword: String, newPassword: String) = intent {
        when (profileRepository.changePassword(oldPassword, newPassword)) {
            is ApiResponse.Error -> postSideEffect(ProfileSideEffects.ShowToast("Ошибка смены пароля"))
            is ApiResponse.Success -> postSideEffect(ProfileSideEffects.ShowToast("Пароль успешно изменен"))
        }
    }

    fun logout() = intent {
        updateUiState(BaseUIState.Loading)
        authRepository.logout()
        postSideEffect(ProfileSideEffects.Logout)
    }

    fun refreshToken() = intent {

    }

    fun deleteProfile() = intent {
        updateUiState(BaseUIState.Loading)
        when (val result = profileRepository.deleteProfile()) {
            is ApiResponse.Error -> updateUiState(BaseUIState.Error(result.msg))
            is ApiResponse.Success -> {
                authRepository.logout()
                postSideEffect(ProfileSideEffects.Logout)
            }
        }
    }

}