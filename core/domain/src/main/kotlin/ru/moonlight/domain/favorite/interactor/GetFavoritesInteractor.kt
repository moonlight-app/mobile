package ru.moonlight.domain.favorite.interactor

import ru.moonlight.data.api.repository.FavoritesRepository
import javax.inject.Inject

class GetFavoritesInteractor @Inject constructor(
    private val repository: FavoritesRepository,
) {
    operator fun invoke() = repository.getFavorites()
}