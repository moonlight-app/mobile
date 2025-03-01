package ru.moonlight.domain.cart.interactor

import ru.moonlight.data.repository.CartRepository
import javax.inject.Inject

class ChangeChoseStatusInteractor @Inject constructor(
    private val repository: CartRepository,
) {
    suspend fun changeStatusForItem(itemId: Long, status: Boolean) =
        repository.updateChoseItemStatus(id = itemId, status = status)

    suspend fun changeStatusForAllItems(status: Boolean) =
        repository.updateChoseItemStatusForAllItems(status = status)
}