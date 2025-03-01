package ru.moonlight.feature_profile_edit.impl.presentation

sealed class ProfileEditAction {
    data class InitEditProfileState(val name: String, val gender: String, val birthDate: String) : ProfileEditAction()
    data class SaveProfileChanges(val oldName: String, val oldGender: String, val oldDate: String) : ProfileEditAction()
    data class UpdateName(val name: String) : ProfileEditAction()
    data class UpdateDateOfBirth(val date: String) : ProfileEditAction()
    data class UpdateGender(val gender: String) : ProfileEditAction()
}