package ru.moonlight.feature_auth_signin.sign_in.presentation

sealed class SignInSideEffect {
    data object NavigateToProfile : SignInSideEffect()
    data object NavigateToSignUp : SignInSideEffect()
}