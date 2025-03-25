package ru.moonlight.network.api.model.profile

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProfileResponse(
    val email: String,
    val name: String,
    @SerialName("birth_date") val birthDate: String,
    val sex: String
)