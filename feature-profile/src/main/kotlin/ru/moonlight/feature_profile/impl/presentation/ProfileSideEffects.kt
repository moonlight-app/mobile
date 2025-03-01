package ru.moonlight.feature_profile.impl.presentation

internal sealed class ProfileSideEffects {
    data object NavigateToEditProfile: ProfileSideEffects()
    data object NavigateToOrders: ProfileSideEffects()
    data object NavigateToFavorites: ProfileSideEffects()
    data object NavigateToChangePassword: ProfileSideEffects()
    data object Logout: ProfileSideEffects()
}