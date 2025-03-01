package ru.moonlight.feature_profile_edit.impl.presentation

sealed class ProfileEditSideEffect {
    class NavigateBack : ProfileEditSideEffect()
}