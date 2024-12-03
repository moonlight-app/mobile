package ru.moonlight.feature_auth.sign_up.registration.presentation

sealed class RegistrationSideEffect {
    data object OnCodeConfirmed: RegistrationSideEffect()
}