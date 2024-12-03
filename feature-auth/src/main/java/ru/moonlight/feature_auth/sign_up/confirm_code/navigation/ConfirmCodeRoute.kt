package ru.moonlight.feature_auth.sign_up.confirm_code.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import kotlinx.serialization.Serializable
import ru.moonlight.feature_auth.sign_up.confirm_code.ConfirmCodeScreen

@Serializable
data class ConfirmCodeRoute(
    val name: String,
    val sex: String,
    val birthDate: String,
    val email: String,
    val password: String,
)

fun NavController.navigateToConfirmCode(
    name: String,
    sex: String,
    birthDate: String,
    email: String,
    password: String,
    navOptions: NavOptions? = null
) = navigate(
    route = ConfirmCodeRoute(name = name, sex = sex, birthDate = birthDate, email = email, password = password),
    navOptions = navOptions,
)

fun NavGraphBuilder.confirmCodeScreen(
    onContinueClick: () -> Unit,
) {
    composable<ConfirmCodeRoute> {
        val args = it.toRoute<ConfirmCodeRoute>()
        ConfirmCodeScreen(
            onContinueClick = onContinueClick,
            name = args.name,
            sex = args.sex,
            birthDate = args.birthDate,
            email = args.email,
            password = args.password,
        )
    }
}