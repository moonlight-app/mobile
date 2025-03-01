package ru.moonlight.feature_profile_changepassword.impl.presentation

sealed class ChangePasswordSideEffect {
    class ShowToast(val message: String? = null) : ChangePasswordSideEffect()
    class NavigateBack : ChangePasswordSideEffect()
}