package ru.moonlight.domain_model.profile

import ru.moonlight.domain_model.order.OrdersDomainModel

data class ProfileDomainModel(
    val email: String,
    val name: String,
    val gender: String,
    val birthDate: String,
    val orders: List<OrdersDomainModel>
)