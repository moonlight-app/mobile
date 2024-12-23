package ru.moonlight.feature_auth_signup_confirmcode.presentation

sealed class ConfirmCodeSideEffect {
    data object NavigateToLanding : ConfirmCodeSideEffect()
}