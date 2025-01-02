package ru.moonlight.feature_auth_signup_confirmcode.impl.ui.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import ru.moonlight.api.component.LogoWithTextComponent
import ru.moonlight.feature_auth_signup_confirmcode.R

@Composable
internal fun Logo(
    email: String,
    modifier: Modifier = Modifier,
) {
    LogoWithTextComponent(
        modifier = modifier,
        subTitleText = stringResource(R.string.confirmEmail),
        bodyText = stringResource(R.string.codeWasSendedToEmail),
        bodyPart2Text = email,
        bodyPart3Text = stringResource(R.string.checkEmailAndPassCode)
    )
}