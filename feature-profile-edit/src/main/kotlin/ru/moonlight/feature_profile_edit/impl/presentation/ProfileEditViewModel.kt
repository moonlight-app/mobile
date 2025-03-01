package ru.moonlight.feature_profile_edit.impl.presentation

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.moonlight.common.ApiResponse
import ru.moonlight.common.base.BaseUIState
import ru.moonlight.common.base.BaseViewModel
import ru.moonlight.domain.profile.interactor.UpdateProfileInteractor
import javax.inject.Inject

@HiltViewModel
internal class ProfileEditViewModel @Inject constructor(
    private val updateProfileInteractor: UpdateProfileInteractor,
): BaseViewModel<ProfileEditState, ProfileEditSideEffect>(ProfileEditState()) {
    val uiState = baseUiState

    fun dispatch(action: ProfileEditAction) {
        when (action) {
            is ProfileEditAction.InitEditProfileState -> {
                initState(
                    name = action.name,
                    gender = action.gender,
                    birthDate = action.birthDate,
                )
            }
            is ProfileEditAction.UpdateName -> updateName(action.name)
            is ProfileEditAction.UpdateGender -> updateGender(action.gender)
            is ProfileEditAction.UpdateDateOfBirth -> updateBirthDate(action.date)
            is ProfileEditAction.SaveProfileChanges -> {
                saveProfile(
                    oldName = action.oldName,
                    oldGender = action.oldGender,
                    oldDate = action.oldDate,
                )
            }
        }
    }

    private fun initState(name: String, gender: String, birthDate: String) {
        viewModelScope.launch {
            intent {
                updateUiState(BaseUIState.Loading)
                reduce { state.copy(name = name, gender = gender, birthDate = birthDate) }
                updateUiState(BaseUIState.Success)
            }
        }

    }

    private fun updateName(name: String) {
        viewModelScope.launch {
            intent {
                if (uiState.value is BaseUIState.Error) updateUiState(BaseUIState.Idle)
                reduce { state.copy(name = name) }
            }
        }
    }

    private fun updateGender(gender: String) {
        viewModelScope.launch {
            intent {
                if (uiState.value is BaseUIState.Error) updateUiState(BaseUIState.Idle)
                reduce { state.copy(gender = gender) }
            }
        }
    }

    private fun updateBirthDate(birthDate: String) {
        viewModelScope.launch {
            intent {
                if (uiState.value is BaseUIState.Error) updateUiState(BaseUIState.Idle)
                reduce { state.copy(birthDate = birthDate) }
            }
        }
    }

    private fun saveProfile(
        oldName: String,
        oldGender: String,
        oldDate: String,
    ) {
        viewModelScope.launch {
            intent {
                if (state.name.isEmpty() || state.gender.isEmpty() || state.birthDate.isEmpty()) {
                    updateUiState(BaseUIState.Error("Заполните все поля"))
                    return@intent
                }

                val response = updateProfileInteractor.invoke(
                    name = if (oldName == state.name) null else state.name,
                    gender = if (oldGender == state.gender) null else state.gender,
                    birthDate = if (oldDate == state.birthDate) null else state.birthDate,
                )

                when (response) {
                    is ApiResponse.Error -> updateUiState(BaseUIState.Error(response.msg))
                    is ApiResponse.Success -> {
                        updateUiState(BaseUIState.Success)
                        postSideEffect(ProfileEditSideEffect.NavigateBack())
                    }
                }
            }
        }
    }
}