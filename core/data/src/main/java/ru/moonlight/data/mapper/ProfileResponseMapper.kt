package ru.moonlight.data.mapper

import ru.moonlight.domain_model.profile.ProfileDomainModel
import ru.moonlight.network.model.profile.ProfileResponse

fun ProfileResponse.mapToDomain() =
    ProfileDomainModel(
        name = this.name,
        gender = this.sex,
        birthDate = this.birthDate,
        email = this.email,
        orders = emptyList(),
    )