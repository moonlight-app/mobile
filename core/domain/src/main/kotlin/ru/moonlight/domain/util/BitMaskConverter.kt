package ru.moonlight.domain.util

internal inline fun <reified T : Enum<T>> List<T>.toBitMask(): Int? {
    val result = this.fold(0) { mask, item ->
        mask or (1 shl item.ordinal)
    }
    if (result == 0) return null
    return result
}

internal inline fun <reified T : Enum<T>> Int.toEnum(): T? =
    enumValues<T>().find { this == (1 shl it.ordinal) }