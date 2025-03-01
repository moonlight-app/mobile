package ru.moonlight.feature_auth_signup_confirmcode.impl.ui.component

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.delay
import ru.moonlight.api.component.TimerComponent
import ru.moonlight.feature_auth_signup_confirmcode.R

@Composable
internal fun NewCodeButton(
    onTryAgainClick: () -> Unit,
    email: String,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    var remainingTime by remember { mutableStateOf(59) }
    val timerActive = remember { mutableStateOf(true) }

    LaunchedEffect(key1 = timerActive.value) {
        remainingTime = 59
        while (remainingTime > 0) {
            delay(1000)
            remainingTime--
        }
        timerActive.value = false
    }

    val timerText = if (timerActive.value) {
        val seconds = remainingTime % 60
        "(${String.format("%02d", seconds)} ${context.getText(R.string.sec)})"
    } else {
        ""
    }

    TimerComponent(
        onTryAgainClick = {
            if (!timerActive.value) {
                timerActive.value = true
                onTryAgainClick()

                Toast.makeText(
                    context,
                    "${context.getText(R.string.codeWasSendedOn)} \n$email",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                Toast.makeText(
                    context,
                    "${context.getText(R.string.wait_time)} $timerText",
                    Toast.LENGTH_SHORT
                ).show()
            }
        },
        timerText = timerText,
        isEnabled = !timerActive.value,
        modifier = modifier
    )
}