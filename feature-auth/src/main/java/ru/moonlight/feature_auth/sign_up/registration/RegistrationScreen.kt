package ru.moonlight.feature_auth.sign_up.registration

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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.moonlight.feature_auth.R
import ru.moonlight.theme.MoonlightTheme
import ru.moonlight.ui.ButtonComponent
import ru.moonlight.ui.ButtonOutlinedComponent
import ru.moonlight.ui.DropdownMenuComponent
import ru.moonlight.ui.TextAuthComponent
import ru.moonlight.ui.TextFieldComponent
import ru.moonlight.ui.TextFieldPasswordComponent

@Composable
fun RegistrationScreen(
    onCreateAccountClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var name by remember {
        mutableStateOf("")
    }
    var sex by remember {
        mutableStateOf("")
    }
    var age by remember {
        mutableStateOf("")
    }
    var email by remember {
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
            TextAuthComponent(subTitleText = stringResource(R.string.registration))
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(MoonlightTheme.dimens.paddingBetweenComponentsSmallVertical, Alignment.CenterVertically),
            ) {
                TextFieldComponent(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = MoonlightTheme.dimens.paddingFromEdges),
                    value = name,
                    onValueChange = { newValue -> name = newValue },
                    placeholder = stringResource(R.string.name)
                )
                DropdownMenuComponent(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = MoonlightTheme.dimens.paddingFromEdges),
                    onSelected = { newValue -> sex = newValue },
                    placeholder = stringResource(R.string.sex),
                )
                TextFieldComponent(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = MoonlightTheme.dimens.paddingFromEdges),
                    value = age,
                    onValueChange = { newValue -> age = newValue },
                    placeholder = stringResource(R.string.age),
                    keyboardType = KeyboardType.Number,
                )
                TextFieldComponent(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = MoonlightTheme.dimens.paddingFromEdges),
                    value = email,
                    onValueChange = { newValue -> email = newValue },
                    placeholder = stringResource(R.string.email),
                    keyboardType = KeyboardType.Email,
                )
                TextFieldPasswordComponent(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = MoonlightTheme.dimens.paddingFromEdges),
                    value = password,
                    onValueChange = { newValue -> password = newValue },
                    placeholder = stringResource(R.string.password),
                )
            }
            ButtonComponent(
                modifier = Modifier
                    .fillMaxWidth(0.55f),
                onClick = { onCreateAccountClick() },
                text = stringResource(R.string.createAccount),
            )
        }
    }
}