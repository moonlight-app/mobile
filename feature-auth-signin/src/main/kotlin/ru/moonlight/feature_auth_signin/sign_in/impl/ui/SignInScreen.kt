package ru.moonlight.feature_auth_signin.sign_in.impl.ui

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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect
import ru.moonlight.api.theme.MoonlightTheme
import ru.moonlight.common.base.BaseUIState
import ru.moonlight.feature_auth_signin.sign_in.impl.presentation.SignInSideEffect
import ru.moonlight.feature_auth_signin.sign_in.impl.presentation.SignInViewModel
import ru.moonlight.feature_auth_signin.sign_in.impl.ui.component.LoginTextField
import ru.moonlight.feature_auth_signin.sign_in.impl.ui.component.Logo
import ru.moonlight.feature_auth_signin.sign_in.impl.ui.component.PasswordTextField
import ru.moonlight.feature_auth_signin.sign_in.impl.ui.component.SignInButton
import ru.moonlight.feature_auth_signin.sign_in.impl.ui.component.SignUpButton

@Composable
internal fun SignInRoute(
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

    SignInScreen(
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
private fun SignInScreen(
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
            Logo()
            LoginTextField(
                onLoginChange = { newLogin -> updateLogin(newLogin) },
                email = email,
                enable =  uiState !is BaseUIState.Loading,
                isError = uiState is BaseUIState.Error,
            )
            PasswordTextField(
                onPasswordChange = { newPassword -> updatePassword(newPassword) },
                password = password,
                enable = uiState !is BaseUIState.Loading,
                isError = uiState is BaseUIState.Error,
                errorText = if (uiState is BaseUIState.Error) uiState.msg ?: "" else ""
            )
        }
        Column(
            verticalArrangement = Arrangement.spacedBy(
                MoonlightTheme.dimens.paddingBetweenComponentsSmallVertical,
                Alignment.CenterVertically
            ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SignInButton(
                onSignInClick = onSignInClick,
                enable = uiState !is BaseUIState.Loading,
                isLoading = uiState is BaseUIState.Loading,
            )
            SignUpButton(
                onSignUpClick = onSignUpClick,
                uiState !is BaseUIState.Loading
            )
        }
    }
}