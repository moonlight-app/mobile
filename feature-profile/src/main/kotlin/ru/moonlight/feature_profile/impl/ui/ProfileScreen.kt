package ru.moonlight.feature_profile.impl.ui

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import ru.moonlight.api.component.TopAppBarComponent
import ru.moonlight.api.theme.MoonlightTheme
import ru.moonlight.api.widget.button.OutlinedButtonWidget
import ru.moonlight.api.widget.progressbar.ProgressBarWidget
import ru.moonlight.api.widget.text.ButtonTextWidget
import ru.moonlight.common.base.BaseUIState
import ru.moonlight.feature_profile.R
import ru.moonlight.feature_profile.impl.presentation.Orders
import ru.moonlight.feature_profile.impl.presentation.ProfileSideEffects
import ru.moonlight.feature_profile.impl.presentation.ProfileViewModel
import ru.moonlight.feature_profile.impl.ui.component.ChangePasswordButton
import ru.moonlight.feature_profile.impl.ui.component.FavoritesButton
import ru.moonlight.feature_profile.impl.ui.component.OrdersCard
import ru.moonlight.feature_profile.impl.ui.component.ProfileCard
import ru.moonlight.feature_profile.impl.ui.component.ProfileDataButton

@Composable
internal fun ProfileRoute(
    onLogoutClick: () -> Unit,
    onEditProfileClick: (String, String, String) -> Unit,
    onOrderClick: () -> Unit,
    onFavoritesClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    val viewModel = hiltViewModel<ProfileViewModel>()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val state by viewModel.collectAsState()

    var changePasswordDialog by remember { mutableStateOf(false) }
    var showToast by remember { mutableStateOf(false) }
    var toastText by remember { mutableStateOf("") }

    LaunchedEffect(Unit) { viewModel.loadProfile() }

    viewModel.collectSideEffect { sideEffect ->
        when (sideEffect) {
            ProfileSideEffects.NavigateToEditProfile -> onEditProfileClick(state.name, state.sex, state.birthDate)
            ProfileSideEffects.NavigateToFavorites -> onFavoritesClick()
            ProfileSideEffects.NavigateToOrders -> onOrderClick()
            ProfileSideEffects.ChangePassword -> changePasswordDialog = true
            ProfileSideEffects.Logout -> onLogoutClick()
            is ProfileSideEffects.ShowToast -> {
                toastText = sideEffect.msg
                showToast = true
            }
        }
    }

    when (uiState) {
        is BaseUIState.Error -> {
            ErrorScreen(errorMsg = (uiState as BaseUIState.Error).msg ?: "")
        }
        BaseUIState.Loading -> {
            LoadingScreen()
        }
        is BaseUIState.Success -> {
            ProfileScreen(
                modifier = modifier,
                onProfileDetailsClick = viewModel::navigateToEditProfile,
                onOrderClick = viewModel::navigateToOrders,
                onFavoritesClick = viewModel::navigateToFavorites,
                onChangePasswordClick = viewModel::showChangePasswordDialog,
                onLogoutClick = viewModel::logout,
                onConfirmDialog = { oldPassword, newPassword ->
                    viewModel.updatePassword(oldPassword, newPassword)
                    changePasswordDialog = false
                },
                onDismissDialog = { changePasswordDialog = false },
                showChangePasswordDialog = changePasswordDialog,
                email = state.email,
                orders = state.orders,
                name = state.name,
            )
        }
        BaseUIState.Idle -> {}
    }

    if (showToast) {
        Toast.makeText(context, toastText, Toast.LENGTH_LONG).show()
        showToast = false
    }
}

@Composable
private fun ProfileScreen(
    onLogoutClick: () -> Unit,
    onProfileDetailsClick: () -> Unit,
    onOrderClick: () -> Unit,
    onFavoritesClick: () -> Unit,
    onChangePasswordClick: () -> Unit,
    onConfirmDialog: (String, String) -> Unit,
    onDismissDialog: () -> Unit,
    showChangePasswordDialog: Boolean,
    email: String,
    name: String,
    orders: List<Orders>,
    modifier: Modifier = Modifier,
) {
//    if (showChangePasswordDialog) {
//        ChangePasswordDialog(
//            onConfirm = onConfirmDialog,
//            onDismiss = onDismissDialog,
//            title = stringResource(R.string.changingPassword),
//            placeholder1 = stringResource(R.string.inputOldPassword),
//            placeholder2 = stringResource(R.string.inputNewPassword),
//            confirmText = stringResource(R.string.save),
//            dismissText = stringResource(R.string.cancel),
//        )
//    } todo заменить на экран

    Scaffold(
        containerColor = MoonlightTheme.colors.background,
        topBar = { TopAppBarComponent(title = stringResource(R.string.profile)) },
    ) { paddingValues ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(top = paddingValues.calculateTopPadding()),
            verticalArrangement = Arrangement.spacedBy(
                space = MoonlightTheme.dimens.paddingBetweenComponentsSmallVertical,
                alignment = Alignment.CenterVertically,
            )
        ) {
            ProfileCard(
                modifier = Modifier
                    .padding(top = MoonlightTheme.dimens.paddingBetweenComponentsSmallVertical * 2),
                name = name,
                email = email,
            )
            ProfileDataButton(onProfileDetailsClick)
            OrdersCard(
                onClick = onOrderClick,
                list = orders,
            )
            FavoritesButton(onFavoritesClick)
            ChangePasswordButton(onChangePasswordClick)
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = MoonlightTheme.dimens.paddingFromEdges),
                contentAlignment = Alignment.BottomCenter,
            ) {
                OutlinedButtonWidget(
                    onClick = onLogoutClick,
                    text = stringResource(R.string.logout),
                )
            }
        }
    }
}

@Composable
private fun ErrorScreen(
    errorMsg: String,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Spacer(modifier = Modifier.width(100.dp))
        ButtonTextWidget(text = "Ошибошка: $errorMsg")
    }
}

@Composable
private fun LoadingScreen(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        ProgressBarWidget()
    }
}