package ru.moonlight.feature_profile.presentation

import ru.moonlight.domain_model.order.OrdersDomainModel

data class ProfileState(
    val email: String = "",
    val name: String = "",
    val sex: String = "",
    val birthDate: String = "",
    val orders: List<Orders> = emptyList(),
)

//TODO move, when orders will be implemented
data class Orders(
    val title: String,
    val imageUrl: String,
    val status: String,
)

fun OrdersDomainModel.mapToPresentation() =
    Orders(
        title = this.title,
        imageUrl = this.imageUrl,
        status = this.status,
    )