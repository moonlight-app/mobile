package ru.moonlight.feature_auth.sign_up.confirm_code

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import ru.moonlight.feature_auth.R
import ru.moonlight.theme.MoonlightTheme
import ru.moonlight.ui.ButtonComponent
import ru.moonlight.ui.TextAnnotatedComponent
import ru.moonlight.ui.TextAuthComponent
import ru.moonlight.ui.TextFieldOTP

@Composable
fun ConfirmCodeScreen(
    onContinueClick: () -> Unit,
    modifier: Modifier = Modifier,
    email: String = "user@example.com"
) {
    val context = LocalContext.current
    var code by remember {
        mutableStateOf("")
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(color = MoonlightTheme.colors.background),
        contentAlignment = Alignment.TopCenter,
    ) {
        Column(
            modifier = Modifier
                .padding(top = MoonlightTheme.dimens.paddingFromEdges * 7)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(MoonlightTheme.dimens.paddingBetweenComponentsBigVertical, Alignment.CenterVertically),
        ) {
            TextAuthComponent(
                subTitleText = stringResource(R.string.confirmEmail),
                bodyText = stringResource(R.string.codeWasSendedToEmail),
                bodyPart2Text = email,
                bodyPart3Text = stringResource(R.string.checkEmailAndPassCode)
            )
            TextFieldOTP(
                value = code,
                onValueChange = { newValue -> code = newValue },
            )
            ButtonComponent(
                modifier = Modifier
                    .fillMaxWidth(0.55f),
                onClick = { onContinueClick() },
                text = stringResource(R.string.continuee),
            )
            TextAnnotatedComponent(
                onClick = { Toast.makeText(context, "${context.getText(R.string.codeWasSendedOn)} \n$email", Toast.LENGTH_SHORT).show() }, //TODO add code request
                textPart1 = stringResource(R.string.dontGetCode),
                textPart2 = stringResource(R.string.tryAgain),
                textPart3 = "(timer)" //TODO add timer
            )
        }
    }
}