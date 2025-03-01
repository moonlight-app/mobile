package ru.moonlight.feature_order.impl.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import ru.moonlight.api.component.OrderFeedComponent
import ru.moonlight.api.component.OrderUiModel
import ru.moonlight.api.screen.ErrorScreen
import ru.moonlight.api.screen.LoadingScreen
import ru.moonlight.api.theme.MoonlightTheme
import ru.moonlight.common.base.BaseUIState
import ru.moonlight.feature_order.R
import ru.moonlight.feature_order.impl.presentation.OrderAction
import ru.moonlight.feature_order.impl.presentation.OrderViewModel
import ru.moonlight.feature_order.impl.ui.component.OrderTopBar

@Composable
fun OrderRoute(
    onBackClick: () -> Unit,
    onProductClick: (Long) -> Unit,
    onVisitCatalogClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val viewModel: OrderViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val orders = viewModel.orders.collectAsLazyPagingItems()


    LaunchedEffect(true) {
        viewModel.dispatch(OrderAction.LoadData())
    }

    when (uiState) {
        BaseUIState.Idle -> {}
        is BaseUIState.Error -> {
            ErrorScreen(
                onRepeatAttemptClick = {},
                errorMsg = (uiState as BaseUIState.Error).msg ?: "",
            )
        }
        BaseUIState.Loading -> {
            LoadingScreen()
        }
        BaseUIState.Success -> {
            OrderScreen(
                onBackClick = onBackClick,
                onProductClick = onProductClick,
                onCatalogClick = onVisitCatalogClick,
                orders = orders,
                modifier = modifier,
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun OrderScreen(
    onBackClick:() -> Unit,
    onProductClick:(Long) -> Unit,
    onCatalogClick:() -> Unit,
    orders: LazyPagingItems<OrderUiModel>,
    modifier: Modifier = Modifier
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
            modifier = modifier,
            containerColor = MoonlightTheme.colors.background,
            topBar = {
                OrderTopBar(
                    title = stringResource(R.string.orders),
                    onBackClick = onBackClick,
                )
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                OrderFeedComponent(
                    modifier = Modifier,
                    onProductClick = onProductClick,
                    onVisitCatalogClick = onCatalogClick,
                    orderFeedModelList = orders,
                )
            }
        }
    }
}