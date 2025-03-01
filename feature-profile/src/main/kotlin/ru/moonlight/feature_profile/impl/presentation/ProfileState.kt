package ru.moonlight.feature_profile.impl.presentation

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
internal data class ProfileState(
    val email: String = "",
    val name: String = "",
    val sex: String = "",
    val birthDate: String = "",
) : Parcelable