package ru.moonlight.feature_auth_signup_registration.registration.impl.presentation

import ru.moonlight.common.GenderOption

internal data class RegistrationState(
    val name: String = "",
    val sex: GenderOption? = null,
    val birthDate: String = "",
    val email: String = "",
    val password: String = "",
)