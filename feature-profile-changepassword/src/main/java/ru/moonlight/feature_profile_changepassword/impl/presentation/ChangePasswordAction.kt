package ru.moonlight.feature_profile_changepassword.impl.presentation

sealed class ChangePasswordAction {
    class ChangePasswordClick : ChangePasswordAction()
    data class OldPasswordChange(val password: String) : ChangePasswordAction()
    data class NewPasswordChange(val password: String) : ChangePasswordAction()
}