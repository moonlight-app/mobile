package ru.moonlight.feature_profile

import android.util.Log
import android.widget.Toast
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.snapping.SnapPosition
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.PagerDefaults
import androidx.compose.foundation.pager.PagerSnapDistance
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect
import ru.moonlight.common.base.BaseUIState
import ru.moonlight.feature_profile.presentation.Orders
import ru.moonlight.feature_profile.presentation.ProfileSideEffects
import ru.moonlight.feature_profile.presentation.ProfileViewModel
import ru.moonlight.theme.MoonlightTheme
import ru.moonlight.ui.ButtonOutlinedComponent
import ru.moonlight.ui.ChangePasswordDialog
import ru.moonlight.ui.ProgressBarComponent
import ru.moonlight.ui.TopAppBarComponent
import ru.moonlight.utils.bouncingClickable
import kotlin.math.absoluteValue

@Composable
internal fun ProfileScreen(
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
            Column(
                modifier = modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                Spacer(modifier = Modifier.width(100.dp))
                Text("Ошибошка: ${((uiState as BaseUIState.Error).msg)}", fontSize = 20.sp)
            }
            Log.e("TAG", "Profile: ${(uiState as BaseUIState.Error).msg}")
        }
        BaseUIState.Loading -> {
            Box(
                modifier = modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) { ProgressBarComponent() }
        }
        is BaseUIState.Success -> {
            Profile(
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
private fun Profile(
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
    if (showChangePasswordDialog) {
        ChangePasswordDialog(
            onConfirm = onConfirmDialog,
            onDismiss = onDismissDialog,
            title = stringResource(R.string.changingPassword),
            placeholder1 = stringResource(R.string.inputOldPassword),
            placeholder2 = stringResource(R.string.inputNewPassword),
            confirmText = stringResource(R.string.save),
            dismissText = stringResource(R.string.cancel),
        )
    }

    Scaffold(
        containerColor = MoonlightTheme.colors.background,
        topBar = { TopAppBarComponent(title = stringResource(R.string.profile)) },
    ) { paddingValues ->
        val pagerState = rememberPagerState(
            pageCount = { orders.size }
        )

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
            ButtonCard(text = stringResource(R.string.profileDetails), onClick = onProfileDetailsClick)
            OrdersCard(
                onClick = onOrderClick,
                list = orders,
            )
            ButtonCard(text = stringResource(R.string.favorites), onClick = onFavoritesClick)
            ButtonCard(text = stringResource(R.string.changePassword), onClick = onChangePasswordClick)
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = MoonlightTheme.dimens.paddingFromEdges),
                contentAlignment = Alignment.BottomCenter,
            ) {
                ButtonOutlinedComponent(
                    onClick = onLogoutClick,
                    text = stringResource(R.string.logout),
                )
            }
        }
    }
}

@Composable
fun ProfileCard(
    name: String,
    email: String,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(horizontal = MoonlightTheme.dimens.paddingFromEdges),
        horizontalArrangement = Arrangement.spacedBy(
            space = MoonlightTheme.dimens.paddingBetweenComponentsHorizontal,
            alignment = Alignment.Start,
        ),
        verticalAlignment = Alignment.Top
    ) {
        Box(
            modifier = Modifier
                .size(68.dp)
                .clip(CircleShape)
                .background(color = MoonlightTheme.colors.highlightComponent)
        )
        Column(
            modifier = Modifier
                .height(68.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = name,
                style = MoonlightTheme.typography.secondTitle,
                color = MoonlightTheme.colors.text,
            )
            Text(
                text = email,
                style = MoonlightTheme.typography.button,
                color = MoonlightTheme.colors.text,
            )
        }
    }
}

@Composable
private fun ButtonCard(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(horizontal = MoonlightTheme.dimens.paddingBetweenComponentsHorizontal)
            .clip(shape = MoonlightTheme.shapes.buttonShape)
            .bouncingClickable { onClick() },
        shape = MoonlightTheme.shapes.buttonShape,
        colors = CardDefaults.cardColors(
            containerColor = MoonlightTheme.colors.card2,
            contentColor = MoonlightTheme.colors.text,
        )
    ) {
        Box(
            modifier = Modifier
                .padding(MoonlightTheme.dimens.paddingBetweenComponentsHorizontal * 2),
            contentAlignment = Alignment.CenterStart,
        ) {
            Text(
                text = text,
                style = MoonlightTheme.typography.subTitle,
            )
        }
    }
}

@Composable
fun OrdersCard(
    onClick: () -> Unit,
    list: List<Orders>,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .clip(shape = MoonlightTheme.shapes.buttonShape)
            .padding(horizontal = MoonlightTheme.dimens.paddingBetweenComponentsHorizontal)
            .bouncingClickable { onClick() },
        shape = MoonlightTheme.shapes.buttonShape,
        colors = CardDefaults.cardColors(
            containerColor = MoonlightTheme.colors.card2,
            contentColor = MoonlightTheme.colors.text,
        )
    ) {
        Column (
            modifier = Modifier
                .padding(vertical = MoonlightTheme.dimens.paddingBetweenComponentsHorizontal),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(
                space = MoonlightTheme.dimens.paddingBetweenComponentsSmallVertical,
            )
        ) {
            Text(
                modifier = Modifier.padding(
                    start = MoonlightTheme.dimens.paddingBetweenComponentsHorizontal
                ),
                text = stringResource(R.string.orders),
                style = MoonlightTheme.typography.subTitle,
            )
            OrderPager(list = list)
        }
    }
}


@Composable
private fun OrderPager(
    list: List<Orders>,
    modifier: Modifier = Modifier,
) {
    val pagerState = rememberPagerState(
        pageCount = { list.size }
    )

    val flingBehavior = PagerDefaults.flingBehavior(
        state = pagerState,
        pagerSnapDistance = PagerSnapDistance.atMost(1),
        snapAnimationSpec = tween(
            easing = FastOutSlowInEasing,
            durationMillis = 400
        ),
    )

    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState.currentPage }
            .collect { page ->
                Log.d("PagerState", "Current page: $page")
            }
    }

    if (list.isNotEmpty()) {
        HorizontalPager(
            modifier = modifier
                .fillMaxWidth(),
            state = pagerState,
            pageSpacing = MoonlightTheme.dimens.paddingBetweenComponentsHorizontal,
            flingBehavior = flingBehavior,
            pageSize = object : PageSize {
                override fun Density.calculateMainAxisPageSize(
                    availableSpace: Int,
                    pageSpacing: Int
                ): Int {
                    return ((availableSpace - 2 * pageSpacing) * 0.90f).toInt()
                }
            },
            snapPosition = SnapPosition.Center,
            key = { page -> page },
        ) { page ->
            val order = list[page]
            OrderItem(
                modifier = Modifier
                    //delete when Horizontal Pager will be fixed with contentPadding and SnapPosition.Center
                    .padding(
                        start = if (page == 0) MoonlightTheme.dimens.paddingBetweenComponentsHorizontal else 0.dp,
                        end = if (page == list.size - 1) MoonlightTheme.dimens.paddingBetweenComponentsHorizontal else 0.dp
                    )
                    .graphicsLayer {
                        val pageOffSet = (
                                (pagerState.currentPage - page) + pagerState
                                    .currentPageOffsetFraction
                                ).absoluteValue
                        alpha = lerp(
                            start = 0.5f,
                            stop = 1f,
                            fraction = 1f - pageOffSet.coerceIn(0f, 1f)
                        )
                        scaleY = lerp(
                            start = 0.9f,
                            stop = 1f,
                            fraction = 1f - pageOffSet.coerceIn(0f, 1f)
                        )
                    },
                title = order.title,
                status = order.status,
            )
        }
    }
}

@Composable
fun OrderItem(
    title: String,
    status: String,
    modifier: Modifier = Modifier
) {
    OutlinedCard(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        shape = MoonlightTheme.shapes.buttonShape,
        border = BorderStroke(
            1.dp,
            //TODO заменить, когда в модели Orders будут понятны статусы
            color = if (status != "бла") MoonlightTheme.colors.highlightComponent
                    else MoonlightTheme.colors.disabledComponent
        ),
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent,
            contentColor = MoonlightTheme.colors.text,
        )
    ) {
        Row(
            modifier = Modifier
                .padding(MoonlightTheme.dimens.paddingBetweenComponentsHorizontal)
                .fillMaxWidth()
                .wrapContentHeight(),
            horizontalArrangement = Arrangement.spacedBy(
                space = MoonlightTheme.dimens.paddingBetweenComponentsHorizontal,
                alignment = Alignment.Start,
            ),
            verticalAlignment = Alignment.Top
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp, 36.dp)
                    .clip(MoonlightTheme.shapes.textFieldShape)
                    .background(color = MoonlightTheme.colors.highlightComponent)
            )
            Column(
                modifier = Modifier
                    .height(36.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth(),
                    text = title,
                    style = MoonlightTheme.typography.subTitle,
                    color = MoonlightTheme.colors.text,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                Text(
                    text = status,
                    style = MoonlightTheme.typography.description,
                    color = MoonlightTheme.colors.highlightComponent,
                )
            }
        }
    }
}