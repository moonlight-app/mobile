package ru.moonlight.feature_auth_signin.sign_in.impl.ui.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import ru.moonlight.api.component.LogoWithTextComponent
import ru.moonlight.feature_auth_signin.R

@Composable
internal fun Logo(modifier: Modifier = Modifier) {
    LogoWithTextComponent(
        modifier = modifier,
        subTitleText = stringResource(R.string.welcome)
    )
}