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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect
import ru.moonlight.api.theme.MoonlightTheme
import ru.moonlight.common.GenderOption
import ru.moonlight.common.base.BaseUIState
import ru.moonlight.feature_auth_signup_registration.registration.api.navigation.presentation.RegistrationAction
import ru.moonlight.feature_auth_signup_registration.registration.api.navigation.presentation.RegistrationSideEffect
import ru.moonlight.feature_auth_signup_registration.registration.api.navigation.presentation.RegistrationViewModel
import ru.moonlight.feature_auth_signup_registration.registration.impl.ui.component.CreateAccountButton
import ru.moonlight.feature_auth_signup_registration.registration.impl.ui.component.DateOfBirthCalendar
import ru.moonlight.feature_auth_signup_registration.registration.impl.ui.component.GenderField
import ru.moonlight.feature_auth_signup_registration.registration.impl.ui.component.LoginTextField
import ru.moonlight.feature_auth_signup_registration.registration.impl.ui.component.Logo
import ru.moonlight.feature_auth_signup_registration.registration.impl.ui.component.NameTextField
import ru.moonlight.feature_auth_signup_registration.registration.impl.ui.component.PasswordTextField

@Composable
internal fun RegistrationRoute(
    onCreateAccountClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: RegistrationViewModel,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val state by viewModel.collectAsState()

    viewModel.collectSideEffect { sideEffect ->
        when (sideEffect) {
            RegistrationSideEffect.OnCodeConfirmed -> onCreateAccountClick()
            RegistrationSideEffect.NavigateToLanding -> {}
        }
    }

    RegistrationScreen(
        modifier = modifier,
        onNameChange = { newName -> viewModel.dispatch(RegistrationAction.UpdateName(newName)) },
        onEmailChange = { newEmail -> viewModel.dispatch(RegistrationAction.UpdateEmail(newEmail)) },
        onSexChange = { newSex -> viewModel.dispatch(RegistrationAction.UpdateSex(newSex)) },
        onBirthDateChange = { newBirthDate -> viewModel.dispatch(RegistrationAction.UpdateBirthDate(newBirthDate)) },
        onPasswordChange = { newPassword -> viewModel.dispatch(RegistrationAction.UpdatePassword(newPassword)) },
        onRequestCodeClick = { viewModel.dispatch(RegistrationAction.RequestCode()) },
        uiState = uiState,
        name = state.name,
        sex = state.sex,
        birthDate = state.birthDate,
        email = state.email,
        password = state.password,
    )
}

@Composable
private fun RegistrationScreen(
    onNameChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onSexChange: (GenderOption) -> Unit,
    onBirthDateChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onRequestCodeClick: () -> Unit,
    uiState: BaseUIState,
    name: String,
    sex: GenderOption?,
    birthDate: String,
    email: String,
    password: String,
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
            enable = uiState !is BaseUIState.Loading && uiState !is BaseUIState.Error,
            isLoading = uiState is BaseUIState.Loading,
        )
    }
}


