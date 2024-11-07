package ru.moonlight.feature_profile

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import ru.moonlight.ui.ButtonComponent

@Composable
internal fun ProfileScreen(
    onLogoutClick: () -> Unit,
    onSignInClick: () -> Unit,
    isUserAuthorize: State<Boolean>,
    modifier: Modifier = Modifier
) {
    val viewModel = hiltViewModel<ProfileViewModel>()

    Column(
        modifier = modifier
            .fillMaxSize(),
    ) {
        Log.d("TAG", "ProfileScreen: ${isUserAuthorize.toString()}")
        if (isUserAuthorize.value) {
            Text(
                text = "ProfileScreen",
                fontSize = 30.sp,
            )
            ButtonComponent(
                onClick = {
                    viewModel.logout()
                    onLogoutClick()
                },
                text = "Выйти",
            )

        } else {
            Text(
                text = "Вы еще не вошли в акк",
                fontSize = 30.sp,
            )
            ButtonComponent(
                onClick = {
                    viewModel.logout()
                    onSignInClick()
                },
                text = "Войти",
            )
        }
    }


}