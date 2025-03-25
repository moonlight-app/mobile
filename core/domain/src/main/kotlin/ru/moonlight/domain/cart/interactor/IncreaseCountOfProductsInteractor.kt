package ru.moonlight.domain.cart.interactor

import ru.moonlight.data.api.repository.CartRepository
import javax.inject.Inject

class IncreaseCountOfProductsInteractor @Inject constructor(
    private val repository: CartRepository,
) {
    companion object {
        private const val MAX_PRODUCT_COUNT = 10
    }

    suspend operator fun invoke(itemId: Long, currentCount: Int) {
        if (currentCount == MAX_PRODUCT_COUNT) return;

        val count = calculateCount(currentCount)
        repository.updateCountOfProducts(itemId = itemId, count = count)
    }

    private fun calculateCount(currentCount: Int): Int {
        return if (isCountIncreasable(currentCount))
            currentCount + 1
        else
            MAX_PRODUCT_COUNT
    }

    private fun isCountIncreasable(count: Int): Boolean = (count < MAX_PRODUCT_COUNT)
}