package ru.moonlight.feature_profile_changepassword.impl.presentation

data class ChangePasswordState(
    val oldPassword: String = "",
    val newPassword: String = "",
)