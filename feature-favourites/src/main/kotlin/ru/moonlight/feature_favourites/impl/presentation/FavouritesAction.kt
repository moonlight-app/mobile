package ru.moonlight.feature_favourites.impl.presentation

sealed class FavouritesAction {
    class LoadData : FavouritesAction()
    data class ChangeProductFavoriteStatus(val id: Long, val status: Boolean) : FavouritesAction()
}