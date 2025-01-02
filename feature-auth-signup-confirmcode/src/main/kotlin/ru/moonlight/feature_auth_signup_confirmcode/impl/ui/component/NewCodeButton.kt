package ru.moonlight.feature_auth_signup_confirmcode.impl.ui.component

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import ru.moonlight.api.component.TimerComponent
import ru.moonlight.feature_auth_signup_confirmcode.R

@Composable
internal fun NewCodeButton(
    email: String,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    TimerComponent(
        onTryAgainClick = {
            Toast.makeText(
                context,
                "${context.getText(R.string.codeWasSendedOn)} \n$email",
                Toast.LENGTH_SHORT
            ).show()
        }, //TODO add code request
        timerText = "(timer)" //TODO add timer
    )
}