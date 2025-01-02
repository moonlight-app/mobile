package ru.moonlight.feature_profile_edit.api.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import kotlinx.serialization.Serializable
import ru.moonlight.feature_profile_edit.impl.ui.ProfileEditRoute

@Serializable
data class ProfileEditRoute(
    val name: String,
    val sex: String,
    val birthDate: String,
)

fun NavController.navigateToProfileEdit(
    name: String,
    sex: String,
    birthDate: String,
    navOptions: NavOptions? = null,
) = navigate(route = ProfileEditRoute(name, sex, birthDate), navOptions = navOptions)

fun NavGraphBuilder.profileEditScreen(
    onBackClick: () -> Unit,
) {
    composable<ProfileEditRoute> {
        val args = it.toRoute<ProfileEditRoute>()
        ProfileEditRoute(
            onBackClick = onBackClick,
            name = args.name,
            gender = args.sex,
            birthDate = args.birthDate,
        )
    }
}