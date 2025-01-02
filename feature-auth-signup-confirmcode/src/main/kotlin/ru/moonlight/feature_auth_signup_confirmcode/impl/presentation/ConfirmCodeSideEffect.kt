package ru.moonlight.feature_auth_signup_confirmcode.impl.presentation

internal sealed class ConfirmCodeSideEffect {
    data object NavigateToLanding : ConfirmCodeSideEffect()
}