package ru.moonlight.feature_profile.presentation

sealed class ProfileSideEffects {
    data object NavigateToEditProfile: ProfileSideEffects()
    data object NavigateToOrders: ProfileSideEffects()
    data object NavigateToFavorites: ProfileSideEffects()
    data object ChangePassword: ProfileSideEffects()
    data object Logout: ProfileSideEffects()
    data class ShowToast(val msg: String): ProfileSideEffects()
}