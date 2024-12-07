package ru.moonlight.feature_profile.presentation

data class ProfileState(
    val email: String = "",
    val name: String = "",
    val sex: String = "",
    val birthDate: String = "",
    val orders: List<Orders> = emptyList(),
)

//TODO move, when orders will be implemented
data class Orders(
    val name: String,
    val status: String,
)