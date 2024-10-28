package ru.moonlight.feature_auth.sign_in.navigation

sealed interface  SignInDirection {
    object toSignUp: SignInDirection
    object toCatalog: SignInDirection
}