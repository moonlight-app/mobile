package ru.moonlight.feature_profile.impl.presentation

internal sealed class ProfileAction {
    object LoadProfile : ProfileAction()
    object ProfileDataClick : ProfileAction()
    object FavouritesClick : ProfileAction()
    object OrderClick : ProfileAction()
    object ChangePasswordClick : ProfileAction()
    object LogoutClick : ProfileAction()
}