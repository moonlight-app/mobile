package ru.moonlight.feature_auth.sign_in

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import ru.moonlight.feature_auth.R
import ru.moonlight.theme.MoonlightTheme
import ru.moonlight.ui.ButtonComponent
import ru.moonlight.ui.ButtonOutlinedComponent
import ru.moonlight.ui.TextAuthComponent
import ru.moonlight.ui.TextFieldComponent
import ru.moonlight.ui.TextFieldPasswordComponent

@Composable
fun SignInScreen(
    onAuthorizeClick: () -> Unit,
    onRegistrationClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var login by remember {
        mutableStateOf("")
    }

    var password by remember {
        mutableStateOf("")
    }

    Box(
        modifier
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
            TextAuthComponent(subTitleText = stringResource(R.string.welcome))
            Column {
                TextFieldComponent(
                    modifier = Modifier
                        .padding(horizontal = MoonlightTheme.dimens.paddingFromEdges),
                    onValueChange = { newLogin -> login = newLogin},
                    value = login,
                    placeholder = stringResource(R.string.email),
                    keyboardType = KeyboardType.Email,
                )
                Spacer(modifier = Modifier.height(MoonlightTheme.dimens.paddingBetweenComponentsSmallVertical))
                TextFieldPasswordComponent(
                    modifier = Modifier
                        .padding(horizontal = MoonlightTheme.dimens.paddingFromEdges),
                    onValueChange = { newPassword -> password = newPassword},
                    value = password,
                    placeholder = stringResource(R.string.password),
                )
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ButtonComponent(
                    modifier = Modifier
                        .fillMaxWidth(0.55f),
                    onClick = { onAuthorizeClick() },
                    text = stringResource(R.string.signInAccount)
                )
                Spacer(modifier = Modifier.height(MoonlightTheme.dimens.paddingBetweenComponentsSmallVertical))
                ButtonOutlinedComponent(
                    modifier = Modifier
                        .fillMaxWidth(0.55f),
                    onClick = { onRegistrationClick() },
                    text = stringResource(R.string.createAccount)
                )
            }
        }
    }
}