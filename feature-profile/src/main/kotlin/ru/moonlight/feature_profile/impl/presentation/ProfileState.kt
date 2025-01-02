package ru.moonlight.feature_profile.impl.presentation

import ru.moonlight.domain_model.order.OrdersDomainModel

internal data class ProfileState(
    val email: String = "",
    val name: String = "",
    val sex: String = "",
    val birthDate: String = "",
    val orders: List<Orders> = emptyList(),
)

//TODO move, when orders will be implemented
internal data class Orders(
    val title: String,
    val imageUrl: String,
    val status: String,
)

internal fun OrdersDomainModel.mapToPresentation() =
    Orders(
        title = this.title,
        imageUrl = this.imageUrl,
        status = this.status,
    )