package ru.moonlight.feature_profile_changepassword.impl.ui

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect
import ru.moonlight.api.component.ConfirmExitAlertDialogComponent
import ru.moonlight.api.component.TopAppBarComponent
import ru.moonlight.api.theme.MoonlightTheme
import ru.moonlight.common.base.BaseUIState
import ru.moonlight.feature_profile_changepassword.R
import ru.moonlight.feature_profile_changepassword.impl.presentation.ChangePasswordAction
import ru.moonlight.feature_profile_changepassword.impl.presentation.ChangePasswordSideEffect
import ru.moonlight.feature_profile_changepassword.impl.presentation.ChangePasswordViewModel
import ru.moonlight.feature_profile_changepassword.impl.ui.components.ChangePasswordButton
import ru.moonlight.feature_profile_changepassword.impl.ui.components.ConfirmNewPasswordField
import ru.moonlight.feature_profile_changepassword.impl.ui.components.NewPasswordField
import ru.moonlight.feature_profile_changepassword.impl.ui.components.OldPasswordField

@Composable
internal fun ChangePasswordRoute(
    onBackClick:() -> Unit,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    val viewModel: ChangePasswordViewModel = hiltViewModel()
    val state by viewModel.collectAsState()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    viewModel.collectSideEffect { sideEffect ->
        when (sideEffect) {
            is ChangePasswordSideEffect.ShowToast -> {
                Toast.makeText(context, context.getString(R.string.passwordChangedSuccessfully), Toast.LENGTH_SHORT)
                    .show()
            }
            is ChangePasswordSideEffect.NavigateBack -> onBackClick()
        }
    }

    var showExitDialog by remember { mutableStateOf(false) }

    BackHandler {
        if (state.oldPassword.isEmpty() && state.newPassword.isEmpty()) onBackClick()
        else showExitDialog = true
    }

    ChangePasswordScreen(
        onBackClick = {
            if (state.oldPassword.isEmpty() && state.newPassword.isEmpty()) onBackClick()
            else showExitDialog = true
        },
        onSaveClick = { viewModel.dispatch(ChangePasswordAction.ChangePasswordClick()) },
        onOldPasswordChange = { oldPassword -> viewModel.dispatch(ChangePasswordAction.OldPasswordChange(oldPassword)) },
        oldPassword = state.oldPassword,
        onNewPasswordChange = { newPassword -> viewModel.dispatch(ChangePasswordAction.NewPasswordChange(newPassword)) },
        newPassword = state.newPassword,
        onConfirmDialog = {
            showExitDialog = false
            onBackClick()
        },
        onDismissDialog = { showExitDialog = false },
        showDialog = showExitDialog,
        uiState = uiState,
        modifier = modifier,
    )

}

@Composable
private fun ChangePasswordScreen(
    onBackClick: () -> Unit,
    onSaveClick: () -> Unit,
    onOldPasswordChange: (String) -> Unit,
    onNewPasswordChange: (String) -> Unit,
    onConfirmDialog: () -> Unit,
    onDismissDialog: () -> Unit,
    oldPassword: String,
    newPassword: String,
    showDialog: Boolean,
    uiState: BaseUIState,
    modifier: Modifier = Modifier,
) {
    var saveBtnEnabled = oldPassword.isNotEmpty() && newPassword.isNotEmpty()

    if (showDialog) {
        ConfirmExitAlertDialogComponent(
            onConfirmExit = onConfirmDialog,
            onDismiss = onDismissDialog,
            title = stringResource(R.string.exitWithoutSave),
            text = stringResource(R.string.areYouSureExitWithoutSaveChanges),
            confirmText = stringResource(R.string.exit),
            dismissText = stringResource(R.string.cancel),
        )
    }

    Scaffold(
        modifier = modifier,
        containerColor = MoonlightTheme.colors.background,
        topBar = {
            TopAppBarComponent(
                onBackClick = onBackClick,
                title = stringResource(R.string.changePassword),
            )
        }
    ) { paddingValues ->
        var isNewPasswordEqualsConfirmPassword by remember{
            mutableStateOf(false)
        }

        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = MoonlightTheme.dimens.paddingBetweenComponentsHorizontal),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(
                MoonlightTheme.dimens.paddingBetweenComponentsSmallVertical - 8.dp,
            ),
        ) {
            OldPasswordField(
                onPasswordChange = onOldPasswordChange,
                password = "",
                isError = uiState is BaseUIState.Error,
            )
            NewPasswordField(
                onPasswordChange = onNewPasswordChange,
                password = "",
                isError = uiState is BaseUIState.Error,
            )
            ConfirmNewPasswordField(
                isNewPasswordEqualsConfirmPassword = { isNewPasswordEqualsConfirmPassword = it },
                newPassword = newPassword,
                password = "",
                isError = uiState is BaseUIState.Error || isNewPasswordEqualsConfirmPassword,
            )
            ChangePasswordButton(
                onSaveClick = onSaveClick,
                enable = saveBtnEnabled,
            )
        }
    }
}