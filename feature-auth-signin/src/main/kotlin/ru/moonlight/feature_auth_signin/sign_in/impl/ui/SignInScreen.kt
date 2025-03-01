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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect
import ru.moonlight.api.theme.MoonlightTheme
import ru.moonlight.common.base.BaseUIState
import ru.moonlight.feature_auth_signin.sign_in.impl.presentation.SignInAction
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
    viewModel: SignInViewModel = hiltViewModel()
) {
    val state by viewModel.collectAsState()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    viewModel.collectSideEffect { sideEffect ->
        when (sideEffect) {
            SignInSideEffect.NavigateToProfile -> onAuthorizeClick()
            SignInSideEffect.NavigateToSignUp -> onRegistrationClick()
        }
    }

    SignInScreen(
        onSignInClick = { viewModel.dispatch(SignInAction.SignInClick) },
        onSignUpClick = { viewModel.dispatch(SignInAction.RegistrationClick) },
        updateLogin = { viewModel.dispatch(SignInAction.UpdateLogin(login = it)) },
        updatePassword = { viewModel.dispatch(SignInAction.UpdatePassword(password = it)) },
        uiState = uiState,
        email = state.email,
        password = state.password,
        modifier = modifier,
    )
}

@Composable
private fun SignInScreen(
    onSignInClick: () -> Unit,
    onSignUpClick: () -> Unit,
    updateLogin: (String) -> Unit,
    updatePassword: (String) -> Unit,
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