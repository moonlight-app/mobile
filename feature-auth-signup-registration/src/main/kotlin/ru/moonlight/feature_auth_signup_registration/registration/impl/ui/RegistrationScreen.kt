package ru.moonlight.feature_auth_signup_registration.registration.impl.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect
import ru.moonlight.api.theme.MoonlightTheme
import ru.moonlight.common.GenderOption
import ru.moonlight.common.base.BaseUIState
import ru.moonlight.feature_auth_signup_registration.registration.impl.presentation.RegistrationSideEffect
import ru.moonlight.feature_auth_signup_registration.registration.impl.presentation.RegistrationViewModel
import ru.moonlight.feature_auth_signup_registration.registration.impl.ui.component.CreateAccountButton
import ru.moonlight.feature_auth_signup_registration.registration.impl.ui.component.DateOfBirthCalendar
import ru.moonlight.feature_auth_signup_registration.registration.impl.ui.component.GenderField
import ru.moonlight.feature_auth_signup_registration.registration.impl.ui.component.LoginTextField
import ru.moonlight.feature_auth_signup_registration.registration.impl.ui.component.Logo
import ru.moonlight.feature_auth_signup_registration.registration.impl.ui.component.NameTextField
import ru.moonlight.feature_auth_signup_registration.registration.impl.ui.component.PasswordTextField

@Composable
internal fun RegistrationRoute(
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

    RegistrationScreen(
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
private fun RegistrationScreen(
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
            Logo()
            NameTextField(
                onNameChange = { newValue -> onNameChange(newValue) },
                name = name,
                enable = uiState !is BaseUIState.Loading,
                isError = uiState is BaseUIState.Error,
            )
            GenderField(
                onGenderChoose = { newValue -> onSexChange(newValue) },
                gender = sex,
                enable = uiState !is BaseUIState.Loading,
                isError = uiState is BaseUIState.Error,
            )
            DateOfBirthCalendar(
                onDateSelected = { date -> onBirthDateChange(date) },
                onFieldClick = { isCalendarOpen = true },
                onCalendarDismiss = { isCalendarOpen = false },
                date = birthDate,
                isCalendarOpen = isCalendarOpen,
                enable = uiState !is BaseUIState.Loading,
                isError = uiState is BaseUIState.Error,
            )
            LoginTextField(
                onLoginChange = { newValue -> onEmailChange(newValue) },
                login = email,
                enable = uiState !is BaseUIState.Loading,
                isError = uiState is BaseUIState.Error,
            )
            PasswordTextField(
                onPasswordChange = { newValue -> onPasswordChange(newValue) },
                password = password,
                enable = uiState !is BaseUIState.Loading,
                isError = uiState is BaseUIState.Error,
                errorText = if (uiState is BaseUIState.Error) uiState.msg ?: "" else "",
            )
        }
        CreateAccountButton(
            modifier = Modifier.padding(top = 8.dp),
            onCreateClick = onRequestCodeClick,
            enable = uiState !is BaseUIState.Loading,
            isLoading = uiState is BaseUIState.Loading,
        )
    }
}


