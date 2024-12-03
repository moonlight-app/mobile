package ru.moonlight.feature_auth.sign_up.confirm_code.presentation

sealed class ConfirmCodeSideEffect {
    data object NavigateToLanding : ConfirmCodeSideEffect()
}