package ru.moonlight.domain.favorite.interactor

import ru.moonlight.data.api.repository.FavoritesRepository
import javax.inject.Inject

class ChangeFavoriteProductStatus @Inject constructor(
    private val repository: FavoritesRepository,
) {
    suspend operator fun invoke(id: Long, isFavorite: Boolean) = repository.changeFavouriteStatusByIdTo(id = id, status = isFavorite)
}