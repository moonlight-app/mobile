package ru.moonlight.feature_auth_signin.sign_in

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
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
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect
import ru.moonlight.common.base.BaseUIState
import ru.moonlight.feature_auth_signin.R
import ru.moonlight.feature_auth_signin.sign_in.presentation.SignInSideEffect
import ru.moonlight.feature_auth_signin.sign_in.presentation.SignInViewModel
import ru.moonlight.theme.MoonlightTheme
import ru.moonlight.ui.ButtonComponent
import ru.moonlight.ui.ButtonOutlinedComponent
import ru.moonlight.ui.TextAuthComponent
import ru.moonlight.ui.TextFieldComponent
import ru.moonlight.ui.TextFieldPasswordWithSupportingTextComponent

@Composable
fun SignInScreen(
    onAuthorizeClick: () -> Unit,
    onRegistrationClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val viewModel = hiltViewModel<SignInViewModel>()
    val state by viewModel.collectAsState()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    viewModel.collectSideEffect { sideEffect ->
        when (sideEffect) {
            SignInSideEffect.NavigateToProfile -> onAuthorizeClick()
            SignInSideEffect.NavigateToSignUp -> onRegistrationClick()
        }
    }

    var email by remember {
        mutableStateOf(state.email)
    }

    var password by remember {
        mutableStateOf(state.password)
    }

    SignInView(
        updateLogin = { newLogin ->
            viewModel.updateLogin(newLogin)
            email = newLogin
        },
        updatePassword = { newPassword ->
            viewModel.updatePassword(newPassword)
            password = newPassword
        },
        onSignInClick = viewModel::login,
        onSignUpClick = viewModel::signUp,
        email = email,
        password = password,
        uiState = uiState,
        modifier = modifier,
    )
}

@Composable
private fun SignInView(
    updateLogin: (String) -> Unit,
    updatePassword: (String) -> Unit,
    onSignInClick: () -> Unit,
    onSignUpClick: () -> Unit,
    email: String,
    password: String,
    uiState: BaseUIState,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .statusBarsPadding()
            .verticalScroll(rememberScrollState(), reverseScrolling = true)
            .fillMaxWidth()
            .imePadding()
            .padding(bottom = MoonlightTheme.dimens.paddingBetweenComponentsSmallVertical),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(
            MoonlightTheme.dimens.paddingBetweenComponentsBigVertical,
            Alignment.CenterVertically
        ),
    ) {
        Column(
            modifier = Modifier
                .padding(top = MoonlightTheme.dimens.paddingFromEdges * 6)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(
                MoonlightTheme.dimens.paddingBetweenComponentsSmallVertical,
                Alignment.CenterVertically
            ),
        ) {
            TextAuthComponent(subTitleText = stringResource(R.string.welcome))
            TextFieldComponent(
                modifier = Modifier
                    .padding(horizontal = MoonlightTheme.dimens.paddingFromEdges)
                    .padding(top = MoonlightTheme.dimens.paddingBetweenComponentsBigVertical),
                onValueChange = { newLogin -> updateLogin(newLogin) },
                value = email,
                placeholder = stringResource(R.string.email),
                keyboardType = KeyboardType.Email,
                keyboardCapitalization = KeyboardCapitalization.None,
                enable = uiState !is BaseUIState.Loading,
                isError = uiState is BaseUIState.Error,
            )
            TextFieldPasswordWithSupportingTextComponent(
                modifier = Modifier
                    .padding(horizontal = MoonlightTheme.dimens.paddingFromEdges),
                onValueChange = { newPassword -> updatePassword(newPassword) },
                value = password,
                placeholder = stringResource(R.string.password),
                keyboardType = KeyboardType.Password,
                enable = uiState !is BaseUIState.Loading,
                isError = uiState is BaseUIState.Error,
                errorText = if (uiState is BaseUIState.Error) uiState.msg ?: "" else "",
            )
        }
        Column(
            verticalArrangement = Arrangement.spacedBy(
                MoonlightTheme.dimens.paddingBetweenComponentsSmallVertical,
                Alignment.CenterVertically
            ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ButtonComponent(
                modifier = Modifier
                    .fillMaxWidth(0.55f),
                onClick = onSignInClick,
                text = stringResource(R.string.signInAccount),
                enable = uiState !is BaseUIState.Loading,
                isLoading = uiState is BaseUIState.Loading,
            )
            ButtonOutlinedComponent(
                modifier = Modifier
                    .fillMaxWidth(0.55f),
                onClick = onSignUpClick,
                text = stringResource(R.string.createAccount),
                enable = uiState !is BaseUIState.Loading,
            )
        }
    }
}