package ru.moonlight.data.impl.mapper

import ru.moonlight.domain_model.profile.ProfileDomainModel
import ru.moonlight.network.api.model.profile.ProfileResponse

fun ProfileResponse.mapToDomain() =
    ProfileDomainModel(
        name = this.name,
        gender = this.sex,
        birthDate = this.birthDate,
        email = this.email,
    )