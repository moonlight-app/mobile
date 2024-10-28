package ru.moonlight.feature_auth.sign_up.navigation

sealed interface  SignUpDirection {
    object up: SignUpDirection
    object toCatalog: SignUpDirection
}