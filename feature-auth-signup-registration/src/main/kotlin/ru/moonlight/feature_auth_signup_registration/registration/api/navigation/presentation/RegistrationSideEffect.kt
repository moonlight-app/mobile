package ru.moonlight.feature_auth_signup_registration.registration.api.navigation.presentation

sealed class RegistrationSideEffect {
    data object OnCodeConfirmed: RegistrationSideEffect()
    data object NavigateToLanding: RegistrationSideEffect()
}