package ru.moonlight.feature_auth_signup_registration.registration

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect
import ru.moonlight.common.GenderOption
import ru.moonlight.common.base.BaseUIState
import ru.moonlight.feature_auth_signup_registration.R
import ru.moonlight.feature_auth_signup_registration.registration.presentation.RegistrationSideEffect
import ru.moonlight.feature_auth_signup_registration.registration.presentation.RegistrationViewModel
import ru.moonlight.theme.MoonlightTheme
import ru.moonlight.ui.ButtonComponent
import ru.moonlight.ui.CalendarWithTextFieldComponent
import ru.moonlight.ui.DropdownMenuComponent
import ru.moonlight.ui.TextAuthComponent
import ru.moonlight.ui.TextFieldComponent
import ru.moonlight.ui.TextFieldPasswordWithSupportingTextComponent

@Composable
fun RegistrationScreen(
    onCreateAccountClick: (String, String, String, String, String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val viewModel: RegistrationViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val state by viewModel.collectAsState()
    viewModel.collectSideEffect { sideEffect ->
        when (sideEffect) {
            RegistrationSideEffect.OnCodeConfirmed ->
                onCreateAccountClick(
                    state.name,
                    state.sex!!.name.lowercase(),
                    state.birthDate,
                    state.email,
                    state.password,
                )
        }
    }

    var name by remember {
        mutableStateOf(state.name)
    }

    var sex by remember {
        mutableStateOf(state.sex)
    }

    var birthDate by remember {
        mutableStateOf(state.birthDate)
    }

    var email by remember {
        mutableStateOf(state.email)
    }

    var password by remember {
        mutableStateOf(state.password)
    }

    Registration(
        uiState = uiState,
        name = name,
        sex = sex,
        birthDate = birthDate,
        email = email,
        password = password,
        onNameChange = { newName ->
            viewModel.updateName(newName)
            name = newName
        },
        onEmailChange = { newEmail ->
            viewModel.updateEmail(newEmail)
            email = newEmail
        },
        onSexChange = { newSex ->
            viewModel.updateSex(newSex)
            sex = newSex
        },
        onBirthDateChange = { newBirthDate ->
            viewModel.updateBirthDate(newBirthDate)
            birthDate = newBirthDate
        },
        onPasswordChange = { newPassword ->
            viewModel.updatePassword(newPassword)
            password = newPassword
        },
        onRequestCodeClick = viewModel::requestCode,
        modifier = modifier
    )
}

@Composable
private fun Registration(
    uiState: BaseUIState,
    name: String,
    sex: GenderOption?,
    birthDate: String,
    email: String,
    password: String,
    onNameChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onSexChange: (GenderOption) -> Unit,
    onBirthDateChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onRequestCodeClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var isCalendarOpen by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .statusBarsPadding()
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState(), reverseScrolling = true)
                .padding(top = MoonlightTheme.dimens.paddingFromEdges * 6)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(
                MoonlightTheme.dimens.paddingBetweenComponentsSmallVertical,
                Alignment.CenterVertically,
            )
        ) {
            TextAuthComponent(subTitleText = stringResource(R.string.registration))
            TextFieldComponent(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = MoonlightTheme.dimens.paddingFromEdges)
                    .padding(top = MoonlightTheme.dimens.paddingBetweenComponentsBigVertical),
                value = name,
                onValueChange = { newValue -> onNameChange(newValue) },
                placeholder = stringResource(R.string.name),
                enable = uiState !is BaseUIState.Loading,
                isError = uiState is BaseUIState.Error,
            )
            DropdownMenuComponent(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = MoonlightTheme.dimens.paddingFromEdges),
                value = sex,
                onSelected = { newValue -> onSexChange(newValue) },
                placeholder = stringResource(R.string.sex),
                enable = uiState !is BaseUIState.Loading,
                isError = uiState is BaseUIState.Error,
            )
            CalendarWithTextFieldComponent(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = MoonlightTheme.dimens.paddingFromEdges),
                date = birthDate,
                onDateSelected = { date -> onBirthDateChange(date) },
                isCalendarOpen = isCalendarOpen,
                onClick = { isCalendarOpen = true },
                onCalendarDismiss = { isCalendarOpen = false },
                placeholder = stringResource(R.string.birthDate),
                enable = uiState !is BaseUIState.Loading,
                isError = uiState is BaseUIState.Error,
            )
            TextFieldComponent(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = MoonlightTheme.dimens.paddingFromEdges),
                onValueChange = { newValue -> onEmailChange(newValue) },
                value = email,
                placeholder = stringResource(R.string.email),
                keyboardType = KeyboardType.Email,
                enable = uiState !is BaseUIState.Loading,
                isError = uiState is BaseUIState.Error,
            )
            TextFieldPasswordWithSupportingTextComponent(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = MoonlightTheme.dimens.paddingFromEdges)
                    .imePadding(),
                onValueChange = { newValue -> onPasswordChange(newValue) },
                value = password,
                placeholder = stringResource(R.string.password),
                enable = uiState !is BaseUIState.Loading,
                isError = uiState is BaseUIState.Error,
                errorText = if (uiState is BaseUIState.Error) uiState.msg ?: "" else "",
            )
        }
        if (uiState is BaseUIState.Error) Spacer(modifier = Modifier.height(8.dp))
        ButtonComponent(
            modifier = Modifier
                .navigationBarsPadding()
                .fillMaxWidth(0.55f),
            onClick = onRequestCodeClick,
            text = stringResource(R.string.createAccount),
            enable = uiState !is BaseUIState.Loading,
            isLoading = uiState is BaseUIState.Loading,
        )
    }
}