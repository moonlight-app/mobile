package ru.moonlight.feature_catalog.impl.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults.Indicator
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect
import ru.moonlight.api.animation.TopBarInOutAnimation
import ru.moonlight.api.component.ProductFeed
import ru.moonlight.api.component.ProductFeedModel
import ru.moonlight.api.screen.ErrorScreen
import ru.moonlight.api.theme.MoonlightTheme
import ru.moonlight.api.widget.progressbar.ProgressBarWidget
import ru.moonlight.common.base.BaseUIState
import ru.moonlight.feature_catalog.impl.presentation.CatalogAction
import ru.moonlight.feature_catalog.impl.presentation.CatalogSideEffect
import ru.moonlight.feature_catalog.impl.presentation.CatalogViewModel
import ru.moonlight.feature_catalog.impl.ui.component.CatalogSortBottomSheet
import ru.moonlight.feature_catalog.impl.ui.component.TopAppBar
import ru.moonlight.feature_catalog_filters.api.CatalogFilter
import ru.moonlight.feature_catalog_sort.api.CatalogSortType

@Composable
internal fun CatalogRoute(
    onBackClick: () -> Unit,
    onProductClick: (Long) -> Unit,
    productType: String,
    isUserAuthorized: Boolean,
    modifier: Modifier = Modifier,
    viewModel: CatalogViewModel = hiltViewModel(),
) {
    val state by viewModel.collectAsState()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val totalCountOfProducts by viewModel.totalCountOfProducts.collectAsState()

    if (state.catalogFilter == null) {
        LaunchedEffect(Unit) {
            viewModel.dispatch(CatalogAction.LoadCatalog(productType))
        }
    }

    val productList = viewModel.productPagingData.collectAsLazyPagingItems()

    viewModel.collectSideEffect { sideEffect ->
        when (sideEffect) {
            CatalogSideEffect.NavigateBack -> onBackClick()
            is CatalogSideEffect.NavigateToProduct -> onProductClick(sideEffect.productId)
        }
    }

    when (uiState) {
        is BaseUIState.Error -> {
            ErrorScreen(
                onRepeatAttemptClick = {},
                errorMsg = (uiState as BaseUIState.Error).msg ?: ""
            )
        }
        BaseUIState.Idle -> {}
        BaseUIState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                ProgressBarWidget()
            }
        }
        BaseUIState.Success -> {
            CatalogScreen(
                modifier = modifier,
                onBackClick = { viewModel.dispatch(CatalogAction.BackClick) },
                onFilterApplied = { newFilter ->
                    viewModel.dispatch(CatalogAction.UpdateCatalogFilter(newFilter))
                },
                onSortApplied = { newSortType ->
                    viewModel.dispatch(CatalogAction.UpdateCatalogSortType(newSortType))
                },
                onProductClick = { productId -> viewModel.dispatch(CatalogAction.ProductClick(productId)) },
                onFavouriteClick = { id, status -> viewModel.dispatch(CatalogAction.FavouriteClick(id, !status)) },
                currentCatalogFilter = state.catalogFilter!!,
                currentSortType = state.catalogSort,
                productFeedModelList = productList,
                countOfProducts = totalCountOfProducts,
                isUserAuthorized = isUserAuthorized,
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CatalogScreen(
    onBackClick: () -> Unit,
    onFilterApplied: (CatalogFilter) -> Unit,
    onSortApplied: (CatalogSortType) -> Unit,
    onProductClick: (Long) -> Unit,
    onFavouriteClick: (id: Long, currentStatus: Boolean) -> Unit,
    currentCatalogFilter: CatalogFilter,
    currentSortType: CatalogSortType,
    productFeedModelList: LazyPagingItems<ProductFeedModel>,
    countOfProducts: Int,
    isUserAuthorized: Boolean,
    modifier: Modifier = Modifier,
) {
    val staggeredGridState = rememberLazyStaggeredGridState()
    val coroutineScope = rememberCoroutineScope()
    var isAppBarVisible by rememberSaveable { mutableStateOf(true) }

    LaunchedEffect(staggeredGridState, productFeedModelList) {
        var lastScrollOffset = 0
        var lastVisibleIndex = 0

        snapshotFlow {
            staggeredGridState.firstVisibleItemIndex to staggeredGridState.firstVisibleItemScrollOffset
        }.collect { (currentIndex, currentScrollOffset) ->
            val isLoading = productFeedModelList.loadState.append is LoadState.Loading ||
                    productFeedModelList.loadState.refresh is LoadState.Loading

            if (!isLoading) {
                isAppBarVisible = when {
                    currentIndex < lastVisibleIndex -> true
                    currentIndex > lastVisibleIndex -> false
                    currentScrollOffset < lastScrollOffset -> true
                    else -> false
                }
                lastVisibleIndex = currentIndex
                lastScrollOffset = currentScrollOffset
            }
        }
    }

    LaunchedEffect(Unit) {
        isAppBarVisible = true // ensures that TopAppBar is visible on first render
    }

    var filterVisibility by rememberSaveable {
        mutableStateOf(false)
    }

    var sortBottomSheetVisibility by rememberSaveable {
        mutableStateOf(false)
    }

    BackHandler {
        if (filterVisibility) filterVisibility = false
        else onBackClick()
    }

    val refreshState = rememberPullToRefreshState()
    var isRefreshing by remember { mutableStateOf(false) }

    PullToRefreshBox(
        modifier = modifier,
        isRefreshing = isRefreshing,
        state = refreshState,
        onRefresh = {
            coroutineScope.launch {
                isRefreshing = true
                delay(250)
                productFeedModelList.refresh()
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
            modifier = modifier
                .statusBarsPadding(),
            containerColor = MoonlightTheme.colors.background,
            topBar = {
                TopBarInOutAnimation(isVisible = isAppBarVisible) {
                    TopAppBar(
                        onBackClick = {
                            if (filterVisibility) filterVisibility = false
                            else onBackClick()
                        },
                        onActionClick = { filterVisibility = !filterVisibility },
                        onFilterApplied = { filters ->
                            onFilterApplied(filters)
                            coroutineScope.launch {
                                staggeredGridState.animateScrollToItem(0)
                            }
                            if (filterVisibility) filterVisibility = false
                        },
                        onSortClick = { sortBottomSheetVisibility = true },
                        filterVisibility = filterVisibility,
                        currentCatalogFilter = currentCatalogFilter,
                        countOfItems = countOfProducts,
                    )
                }
            },
            bottomBar = {
                if (sortBottomSheetVisibility) {
                    CatalogSortBottomSheet(
                        onSortTypeChange = { newSortType ->
                            if (currentSortType != newSortType) {
                                onSortApplied(newSortType)
                                coroutineScope.launch {
                                    staggeredGridState.animateScrollToItem(0)
                                }
                            }
                            sortBottomSheetVisibility = false
                        },
                        currentSortType = currentSortType,
                    )
                }
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .padding(top = paddingValues.calculateTopPadding())
                    .fillMaxSize(),
            ) {
                ProductFeed(
                    onProductClick = onProductClick,
                    onFavouriteClick = onFavouriteClick,
                    onChangeFiltersClick = { filterVisibility = !filterVisibility },
                    productFeedModelList = productFeedModelList,
                    scrollState = staggeredGridState,
                    isFavoriteButtonVisible = isUserAuthorized,
                )
            }
        }
    }
}