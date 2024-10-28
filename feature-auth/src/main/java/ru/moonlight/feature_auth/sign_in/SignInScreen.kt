package ru.moonlight.feature_auth.sign_in

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp

@Composable
fun SignInScreen(
    onAuthorizeClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize(),
    ) {
        Text(
            text = "SignInScreen",
            fontSize = 30.sp,
        )
        Button(onClick = { onAuthorizeClick() }) {
            Text(text = "Authorize")
        }

    }
}