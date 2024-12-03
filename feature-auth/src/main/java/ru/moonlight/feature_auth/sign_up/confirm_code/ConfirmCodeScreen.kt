package ru.moonlight.feature_auth.sign_up.confirm_code

import android.widget.Toast
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect
import ru.moonlight.common.base.BaseUIState
import ru.moonlight.feature_auth.R
import ru.moonlight.feature_auth.sign_up.confirm_code.presentation.ConfirmCodeSideEffect
import ru.moonlight.feature_auth.sign_up.confirm_code.presentation.ConfirmCodeViewModel
import ru.moonlight.theme.MoonlightTheme
import ru.moonlight.ui.ButtonComponent
import ru.moonlight.ui.TextAnnotatedComponent
import ru.moonlight.ui.TextAuthComponent
import ru.moonlight.ui.TextFieldOTP

@Composable
fun ConfirmCodeScreen(
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

    ConfirmCodeView(
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
private fun ConfirmCodeView(
    updateCode: (String) -> Unit,
    onConfirmClick: () -> Unit,
    email: String,
    code: String,
    uiState: BaseUIState,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
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
        TextAuthComponent(
            subTitleText = stringResource(R.string.confirmEmail),
            bodyText = stringResource(R.string.codeWasSendedToEmail),
            bodyPart2Text = email,
            bodyPart3Text = stringResource(R.string.checkEmailAndPassCode)
        )
        TextFieldOTP(
            modifier = Modifier.focusRequester(focusRequester),
            value = code,
            onValueChange = { newCode -> updateCode(newCode) },
        )
        ButtonComponent(
            modifier = Modifier
                .fillMaxWidth(0.55f),
            enable = uiState !is BaseUIState.Loading,
            isLoading = uiState is BaseUIState.Loading,
            onClick = onConfirmClick,
            text = stringResource(R.string.continuee),
        )
        TextAnnotatedComponent(
            onClick = { Toast.makeText(context, "${context.getText(R.string.codeWasSendedOn)} \n$email", Toast.LENGTH_SHORT).show() }, //TODO add code request
            textPart1 = stringResource(R.string.dontGetCode),
            textPart2 = stringResource(R.string.tryAgain),
            textPart3 = "(timer)" //TODO add timer
        )
    }
}