package ru.moonlight.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProfileResponse(
    val email: String,
    val name: String,
    @SerialName("birth_date") val birthDate: String,
    val sex: String
)