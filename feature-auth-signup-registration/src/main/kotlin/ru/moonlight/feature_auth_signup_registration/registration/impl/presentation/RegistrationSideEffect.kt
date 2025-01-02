package ru.moonlight.feature_auth_signup_registration.registration.impl.presentation

internal sealed class RegistrationSideEffect {
    data object OnCodeConfirmed: RegistrationSideEffect()
}