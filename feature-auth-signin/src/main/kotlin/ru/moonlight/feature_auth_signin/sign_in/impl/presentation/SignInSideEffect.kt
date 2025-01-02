package ru.moonlight.feature_auth_signin.sign_in.impl.presentation

internal sealed class SignInSideEffect {
    data object NavigateToProfile : SignInSideEffect()
    data object NavigateToSignUp : SignInSideEffect()
}