package ru.moonlight.domain.order.interactor

import ru.moonlight.data.api.repository.OrderRepository
import javax.inject.Inject

class GetOrdersInteractor @Inject constructor(
    private val repository: OrderRepository,
) {
    operator fun invoke() = repository.getOrders()
}