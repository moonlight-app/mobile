package ru.moonlight.feature_cart.impl.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
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
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.moonlight.api.component.CartFeedComponent
import ru.moonlight.api.component.CartFeedModel
import ru.moonlight.api.component.TopAppBarComponent
import ru.moonlight.api.screen.ErrorScreen
import ru.moonlight.api.screen.LoadingScreen
import ru.moonlight.api.theme.MoonlightTheme
import ru.moonlight.api.widget.button.ButtonWidget
import ru.moonlight.api.widget.text.SecondTitleTextWidget
import ru.moonlight.api.widget.text.TextFieldTextWidget
import ru.moonlight.common.base.BaseUIState
import ru.moonlight.feature_cart.R
import ru.moonlight.feature_cart.impl.presentation.CartAction
import ru.moonlight.feature_cart.impl.presentation.CartUIState
import ru.moonlight.feature_cart.impl.presentation.CartViewModel

@Composable
fun CartRoute(
    onBackClick: () -> Unit,
    onVisitCatalogClick: () -> Unit,
    onProductClick: (id: Long) -> Unit,
    onSignInClick: () -> Unit,
    isUserAuthorize: Boolean,
    modifier: Modifier = Modifier,
) {
    val viewModel: CartViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val cartUIstate by viewModel.cartUIState.collectAsStateWithLifecycle()
    val cartItems = viewModel.cartItems.collectAsLazyPagingItems()

    LaunchedEffect(true) {
        viewModel.dispatch(CartAction.ClearUiState())
    }

    if (cartItems.itemCount == 0) {
        LaunchedEffect(key1 = true) {
            viewModel.dispatch(CartAction.LoadCart())
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
        BaseUIState.Idle -> {}
        BaseUIState.Loading -> {
            LoadingScreen()
        }
        BaseUIState.Success -> {
            if (isUserAuthorize)
                CartScreen(
                    modifier = modifier,
                    onVisitCatalogClick = onVisitCatalogClick,
                    onProductClick = { id -> onProductClick(id) },
                    onCheckChange = { id, status -> viewModel.dispatch(CartAction.ChangeChoseStatus(itemId = id, status = status)) },
                    onDeleteClick = { id -> viewModel.dispatch(CartAction.DeleteItemFromCart(itemId = id)) },
                    onFavouriteClick = { id, status -> viewModel.dispatch(CartAction.ChangeProductFavoriteStatus(id = id, status = !status)) },
                    cartItems = cartItems,
                    onIncreaseProductCountClick = { id, currentCount ->
                        viewModel.dispatch(CartAction.IncreaseProductCount(itemId = id, currentCount = currentCount))
                    },
                    onDecreaseProductCountClick = { id, currentCount ->
                        viewModel.dispatch(CartAction.DecreaseProductCount(itemId = id, currentCount = currentCount))
                    },
                    onOrderClick = { viewModel.dispatch(CartAction.CompleteOrder()) },
                    onSharedCheckBoxClick = { status -> viewModel.dispatch(CartAction.ChangeChoseStatusForAllItems(status = status)) },
                    isCheckSharedCheckBox = cartItems.itemSnapshotList.items.all { it.chosen == true },
                    cartUIState = cartUIstate,
                )
            else
                UnauthorizedScreen(
                    modifier = modifier,
                    onSignInClick = onSignInClick,
                )
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CartScreen(
    onVisitCatalogClick: () -> Unit,
    onProductClick: (id: Long) -> Unit,
    onCheckChange: (Long, Boolean) -> Unit,
    onDeleteClick: (Long) -> Unit,
    onFavouriteClick: (id: Long, currentStatus: Boolean) -> Unit,
    onIncreaseProductCountClick:(id: Long, count: Int) -> Unit,
    onDecreaseProductCountClick:(id: Long, count: Int) -> Unit,
    onOrderClick: () -> Unit,
    onSharedCheckBoxClick:(Boolean) -> Unit,
    isCheckSharedCheckBox: Boolean,
    cartUIState: CartUIState,
    cartItems: LazyPagingItems<CartFeedModel>,
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
                cartItems.refresh()
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
            topBar = {
                TopAppBarComponent(
                    title = stringResource(R.string.cart),
                )
            }
        ) { paddingValues ->
            when (cartUIState) {
                is CartUIState.ChooseItems -> {
                    BuildOrderScreen(
                        modifier = Modifier
                            .padding(top = paddingValues.calculateTopPadding()),
                        onProductClick = onProductClick,
                        onOrderClick = onOrderClick,
                        onCheckChange = onCheckChange,
                        onDeleteClick = onDeleteClick,
                        onFavouriteClick = onFavouriteClick,
                        onVisitCatalogClick = onVisitCatalogClick,
                        onIncreaseProductCountClick = onIncreaseProductCountClick,
                        onDecreaseProductCountClick = onDecreaseProductCountClick,
                        onSharedCheckBoxClick = onSharedCheckBoxClick,
                        isCheckSharedCheckBox = isCheckSharedCheckBox,
                        cartItems = cartItems,
                    )
                }
                is CartUIState.OrderCompleted -> {
                    OrderCompletedScreen(
                        modifier = Modifier
                            .padding(top = paddingValues.calculateTopPadding()),
                        onVisitCatalogClick = onVisitCatalogClick,
                    )
                }
            }
        }
    }
}

@Composable
private fun UnauthorizedScreen(
    onSignInClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = modifier,
        containerColor = MoonlightTheme.colors.background,
        topBar = {
            TopAppBarComponent(
                title = stringResource(R.string.cart),
            )
        }
    ) { paddingValues ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues),
            verticalArrangement = Arrangement.spacedBy(
                space = MoonlightTheme.dimens.paddingBetweenComponentsMediumVertical,
            ),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            SecondTitleTextWidget(text = stringResource(R.string.authorize))
            TextFieldTextWidget(text = stringResource(R.string.to_see_cart_sign_in), textAlign = TextAlign.Center)
        }
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.BottomCenter
        ) {
            ButtonWidget(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(horizontal = MoonlightTheme.dimens.paddingBetweenComponentsHorizontal)
                    .padding(bottom = MoonlightTheme.dimens.paddingFromEdges),
                onClick = onSignInClick,
                text = stringResource(R.string.sign_in_account),
            )
        }
    }
}

@Composable
private fun BuildOrderScreen(
    onOrderClick: () -> Unit,
    onProductClick: (id: Long) -> Unit,
    onCheckChange: (Long, Boolean) -> Unit,
    onDeleteClick: (Long) -> Unit,
    onFavouriteClick: (id: Long, currentStatus: Boolean) -> Unit,
    onVisitCatalogClick: () -> Unit,
    onIncreaseProductCountClick:(id: Long, count: Int) -> Unit,
    onDecreaseProductCountClick:(id: Long, count: Int) -> Unit,
    onSharedCheckBoxClick:(Boolean) -> Unit,
    isCheckSharedCheckBox: Boolean,
    cartItems: LazyPagingItems<CartFeedModel>,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize(),
    ) {
        CartFeedComponent(
            onProductClick = onProductClick,
            onCheckChange = onCheckChange,
            onDeleteClick = onDeleteClick,
            onFavouriteClick = onFavouriteClick,
            onVisitCatalogClick = onVisitCatalogClick,
            onOrderClick = onOrderClick,
            onIncreaseProductCountClick = onIncreaseProductCountClick,
            onDecreaseProductCountClick = onDecreaseProductCountClick,
            onSharedCheckBoxClick = onSharedCheckBoxClick,
            isCheckSharedCheckBox = isCheckSharedCheckBox,
            cartFeedModelList = cartItems,
            scrollState = rememberLazyListState(),
        )
    }
}

@Composable
private fun OrderCompletedScreen(
    onVisitCatalogClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(
            space = MoonlightTheme.dimens.paddingBetweenComponentsMediumVertical,
        ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SecondTitleTextWidget(text = stringResource(R.string.order_completed))
        TextFieldTextWidget(
            text = stringResource(R.string.thanks_for_order),
            textAlign = TextAlign.Center,
        )
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = MoonlightTheme.dimens.paddingFromEdges),
        contentAlignment = Alignment.BottomCenter
    ) {
        ButtonWidget(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = MoonlightTheme.dimens.paddingBetweenComponentsHorizontal),
            onClick = onVisitCatalogClick,
            text = stringResource(R.string.visit_catalog)
        )
    }
}