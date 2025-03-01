package ru.moonlight.feature_auth_signin.sign_in.impl.presentation

internal sealed class SignInAction {
    data object SignInClick : SignInAction()
    data object RegistrationClick : SignInAction()
    data class UpdateLogin(val login: String) : SignInAction()
    data class UpdatePassword(val password: String) : SignInAction()
}