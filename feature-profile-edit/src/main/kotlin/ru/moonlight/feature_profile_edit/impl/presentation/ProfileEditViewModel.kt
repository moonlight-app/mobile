package ru.moonlight.feature_profile_edit.impl.presentation

import dagger.hilt.android.lifecycle.HiltViewModel
import ru.moonlight.common.base.BaseUIState
import ru.moonlight.common.base.BaseViewModel
import ru.moonlight.domain.profile.UpdateProfileInteractor
import javax.inject.Inject

@HiltViewModel
internal class ProfileEditViewModel @Inject constructor(
    private val updateProfileInteractor: UpdateProfileInteractor,
): BaseViewModel<ProfileEditState, Nothing>(ProfileEditState()) {
    val uiState = baseUiState

    fun initState(name: String, gender: String, birthDate: String) = intent {
        reduce { state.copy(name = name, gender = gender, birthDate = birthDate) }
        updateUiState(BaseUIState.Success)
    }

    fun updateName(name: String) = intent {
        if (uiState.value is BaseUIState.Error) updateUiState(BaseUIState.Idle)
        reduce { state.copy(name = name) }
    }

    fun updateGender(gender: String) = intent {
        if (uiState.value is BaseUIState.Error) updateUiState(BaseUIState.Idle)
        reduce { state.copy(gender = gender) }
    }

    fun updateBirthDate(birthDate: String) = intent {
        if (uiState.value is BaseUIState.Error) updateUiState(BaseUIState.Idle)
        reduce { state.copy(birthDate = birthDate) }
    }

    fun saveProfile(
        oldName: String,
        oldGender: String,
        oldDate: String,
    ) = intent {
        if (state.name.isEmpty() || state.gender.isEmpty() || state.birthDate.isEmpty()) {
            updateUiState(BaseUIState.Error("Заполните все поля"))
            return@intent
        }
        updateProfileInteractor.invoke(
            name = if (oldName == state.name) null else state.name,
            gender = if (oldGender == state.gender) null else state.gender,
            birthDate = if (oldDate == state.birthDate) null else state.birthDate,
        )
    }
}