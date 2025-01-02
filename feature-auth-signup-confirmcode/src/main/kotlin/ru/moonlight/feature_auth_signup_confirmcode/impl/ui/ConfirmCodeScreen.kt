package ru.moonlight.feature_auth_signup_confirmcode.impl.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect
import ru.moonlight.api.component.OTPComponent
import ru.moonlight.api.theme.MoonlightTheme
import ru.moonlight.common.base.BaseUIState
import ru.moonlight.feature_auth_signup_confirmcode.impl.presentation.ConfirmCodeSideEffect
import ru.moonlight.feature_auth_signup_confirmcode.impl.presentation.ConfirmCodeViewModel
import ru.moonlight.feature_auth_signup_confirmcode.impl.ui.component.ContinueButton
import ru.moonlight.feature_auth_signup_confirmcode.impl.ui.component.Logo
import ru.moonlight.feature_auth_signup_confirmcode.impl.ui.component.NewCodeButton

@Composable
internal fun ConfirmCodeRoute(
    onContinueClick: () -> Unit,
    name: String,
    sex: String,
    birthDate: String,
    email: String,
    password: String,
    modifier: Modifier = Modifier,
) {
    val viewModel: ConfirmCodeViewModel = hiltViewModel()
    val state by viewModel.collectAsState()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    viewModel.collectSideEffect { sideEffect ->
        when (sideEffect) {
            ConfirmCodeSideEffect.NavigateToLanding -> onContinueClick()
        }
    }

    var code by remember {
        mutableStateOf(state.code)
    }

    ConfirmCodeScreen(
        updateCode = { newCode ->
            viewModel.updateCode(newCode)
            code = newCode
        },
        onConfirmClick = { viewModel.confirmCode(code = code, email = email, password = password, name = name, birthDate = birthDate, sex = sex) },
        email = email,
        code = code,
        uiState = uiState,
        modifier = modifier
    )
}

@Composable
private fun ConfirmCodeScreen(
    updateCode: (String) -> Unit,
    onConfirmClick: () -> Unit,
    email: String,
    code: String,
    uiState: BaseUIState,
    modifier: Modifier = Modifier,
) {
    val focusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
        keyboardController?.show()
    }

    Column(
        modifier = modifier
            .statusBarsPadding()
            .navigationBarsPadding()
            .padding(top = MoonlightTheme.dimens.paddingFromEdges * 6)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(
            MoonlightTheme.dimens.paddingBetweenComponentsBigVertical,
            Alignment.CenterVertically
        ),
    ) {
        Logo(email = email)
        OTPComponent(
            modifier = Modifier.focusRequester(focusRequester),
            onValueChange = { newCode -> updateCode(newCode) },
            value = code,
        )
        ContinueButton(
            onContinueClick = onConfirmClick,
            enable = uiState !is BaseUIState.Loading,
            isLoading = uiState is BaseUIState.Loading,
        )
        NewCodeButton(email = email)
    }
}