package ru.moonlight.common

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
enum class GenderOption(val displayName: String): Parcelable {
    MALE("Мужской"),
    FEMALE("Женский");
}

fun String.toGenderOptions(): GenderOption {
    return when (this.lowercase()) {
        "male" -> GenderOption.MALE
        "female" -> GenderOption.FEMALE
        else -> throw IllegalArgumentException("Unknown gender: $this")
    }
}