package ru.moonlight.common

enum class GenderOption(val displayName: String) {
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