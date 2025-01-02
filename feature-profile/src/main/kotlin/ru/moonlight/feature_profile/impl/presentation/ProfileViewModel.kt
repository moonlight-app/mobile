package ru.moonlight.feature_profile.impl.presentation

import dagger.hilt.android.lifecycle.HiltViewModel
import ru.moonlight.common.ApiResponse
import ru.moonlight.common.base.BaseUIState
import ru.moonlight.common.base.BaseViewModel
import ru.moonlight.domain.profile.ChangePasswordInteractor
import ru.moonlight.domain.profile.GetProfileUseCase
import ru.moonlight.domain.profile.LogoutInteractor
import javax.inject.Inject

@HiltViewModel
internal class ProfileViewModel @Inject constructor(
    private val getProfileUseCase: GetProfileUseCase,
    private val changePasswordInteractor: ChangePasswordInteractor,
    private val logoutInteractor: LogoutInteractor,
): BaseViewModel<ProfileState, ProfileSideEffects>(ProfileState()) {
    val uiState = baseUiState

    fun loadProfile() = intent {
        updateUiState(BaseUIState.Loading)
        when (val result = getProfileUseCase.invoke()) {
            is ApiResponse.Error -> updateUiState(BaseUIState.Error(result.msg))
            is ApiResponse.Success -> {
                reduce { state.copy(
                    email = result.data?.email!!,
                    name = result.data?.name!!,
                    sex = result.data?.gender!!,
                    birthDate = result.data?.birthDate!!,
                    orders = result.data?.orders?.map{ order -> order.mapToPresentation() } ?: emptyList(),
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
        when (changePasswordInteractor.invoke(oldPassword, newPassword)) {
            is ApiResponse.Error -> postSideEffect(ProfileSideEffects.ShowToast("Ошибка смены пароля"))
            is ApiResponse.Success -> postSideEffect(ProfileSideEffects.ShowToast("Пароль успешно изменен"))
        }
    }

    fun logout() = intent {
        updateUiState(BaseUIState.Loading)
        logoutInteractor.invoke()
        postSideEffect(ProfileSideEffects.Logout)
    }

}