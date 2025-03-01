package ru.moonlight.feature_profile.impl.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults.Indicator
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect
import ru.moonlight.api.component.OrderUiModel
import ru.moonlight.api.component.TopAppBarComponent
import ru.moonlight.api.screen.ErrorScreen
import ru.moonlight.api.screen.LoadingScreen
import ru.moonlight.api.theme.MoonlightTheme
import ru.moonlight.api.widget.button.OutlinedButtonWidget
import ru.moonlight.common.base.BaseUIState
import ru.moonlight.feature_profile.R
import ru.moonlight.feature_profile.impl.presentation.ProfileAction
import ru.moonlight.feature_profile.impl.presentation.ProfileSideEffects
import ru.moonlight.feature_profile.impl.presentation.ProfileViewModel
import ru.moonlight.feature_profile.impl.ui.component.ChangePasswordButton
import ru.moonlight.feature_profile.impl.ui.component.FavoritesButton
import ru.moonlight.feature_profile.impl.ui.component.OrdersCard
import ru.moonlight.feature_profile.impl.ui.component.ProfileCard
import ru.moonlight.feature_profile.impl.ui.component.ProfileDataButton

@Composable
internal fun ProfileRoute(
    onBackClick: () -> Unit,
    onEditProfileClick: (String, String, String) -> Unit,
    onOrderClick: () -> Unit,
    onFavoritesClick: () -> Unit,
    onChangePasswordClick: () -> Unit,
    onLogoutClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val state by viewModel.collectAsState()
    val orders = viewModel.orders.collectAsLazyPagingItems()

    if (state.name == "" || state.sex == "") {
        LaunchedEffect(Unit) { viewModel.dispatch(ProfileAction.LoadProfile) }
    }

    viewModel.collectSideEffect { sideEffect ->
        when (sideEffect) {
            ProfileSideEffects.NavigateToEditProfile -> onEditProfileClick(state.name, state.sex, state.birthDate)
            ProfileSideEffects.NavigateToFavorites -> onFavoritesClick()
            ProfileSideEffects.NavigateToOrders -> onOrderClick()
            ProfileSideEffects.NavigateToChangePassword -> onChangePasswordClick()
            ProfileSideEffects.Logout -> onLogoutClick()
        }
    }

    BackHandler {
        onBackClick()
    }

    when (uiState) {
        is BaseUIState.Error -> {
            ErrorScreen(
                onRepeatAttemptClick = {},
                errorMsg = (uiState as BaseUIState.Error).msg ?: "",
            )
        }
        BaseUIState.Loading -> {
            LoadingScreen()
        }
        is BaseUIState.Success -> {
            ProfileScreen(
                modifier = modifier,
                onRefresh = { viewModel.dispatch(ProfileAction.LoadProfile) },
                onProfileDetailsClick = { viewModel.dispatch(ProfileAction.ProfileDataClick) },
                onOrderClick = { viewModel.dispatch(ProfileAction.OrderClick) },
                onFavoritesClick = { viewModel.dispatch(ProfileAction.FavouritesClick) },
                onChangePasswordClick = { viewModel.dispatch(ProfileAction.ChangePasswordClick) },
                onLogoutClick = { viewModel.dispatch(ProfileAction.LogoutClick) },
                email = state.email,
                orders = orders,
                name = state.name,
            )
        }
        BaseUIState.Idle -> {}
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ProfileScreen(
    onRefresh: () -> Unit,
    onLogoutClick: () -> Unit,
    onProfileDetailsClick: () -> Unit,
    onOrderClick: () -> Unit,
    onFavoritesClick: () -> Unit,
    onChangePasswordClick: () -> Unit,
    email: String,
    name: String,
    orders: LazyPagingItems<OrderUiModel>,
    modifier: Modifier = Modifier,
) {
    val refreshState = rememberPullToRefreshState()
    val coroutineScope = rememberCoroutineScope()
    var isRefreshing by remember { mutableStateOf(false) }

    PullToRefreshBox(
        modifier = modifier,
        isRefreshing = isRefreshing,
        state = refreshState,
        onRefresh = {
            coroutineScope.launch {
                isRefreshing = true
                delay(250)
                onRefresh()
                orders.refresh()
                isRefreshing = false
            }
        },
        indicator = {
            Indicator(
                modifier = Modifier.align(Alignment.TopCenter),
                isRefreshing = isRefreshing,
                containerColor = MoonlightTheme.colors.highlightComponent,
                color = MoonlightTheme.colors.highlightText,
                state = refreshState
            )
        },
    ) {
        Scaffold(
            containerColor = MoonlightTheme.colors.background,
            topBar = { TopAppBarComponent(title = stringResource(R.string.profile)) },
        ) { paddingValues ->
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .padding(top = paddingValues.calculateTopPadding())
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.SpaceBetween,
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(
                        space = MoonlightTheme.dimens.paddingBetweenComponentsSmallVertical,
                        alignment = Alignment.Top,
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
                        orders = orders,
                    )
                    FavoritesButton(onFavoritesClick)
                    ChangePasswordButton(onChangePasswordClick)
                }

                OutlinedButtonWidget(
                    modifier = Modifier
                        .padding(bottom = MoonlightTheme.dimens.paddingFromEdges)
                        .align(Alignment.CenterHorizontally),
                    onClick = onLogoutClick,
                    text = stringResource(R.string.logout),
                )
            }
        }
    }
}