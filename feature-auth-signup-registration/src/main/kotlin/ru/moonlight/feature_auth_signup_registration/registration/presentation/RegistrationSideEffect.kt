package ru.moonlight.feature_auth_signup_registration.registration.presentation

sealed class RegistrationSideEffect {
    data object OnCodeConfirmed: RegistrationSideEffect()
}