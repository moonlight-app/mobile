package ru.moonlight.feature_catalog.impl.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import org.orbitmvi.orbit.compose.collectAsState
import ru.moonlight.api.animation.TopBarInOutAnimation
import ru.moonlight.api.component.ProductFeed
import ru.moonlight.api.component.ProductFeedModel
import ru.moonlight.api.theme.MoonlightTheme
import ru.moonlight.api.widget.progressbar.ProgressBarWidget
import ru.moonlight.common.base.BaseUIState
import ru.moonlight.feature_catalog.impl.presentation.CatalogViewModel
import ru.moonlight.feature_catalog.impl.ui.component.CatalogSortBottomSheet
import ru.moonlight.feature_catalog.impl.ui.component.TopAppBar
import ru.moonlight.feature_catalog_filters.api.CatalogFilter
import ru.moonlight.feature_catalog_sort.api.CatalogSortType

@Composable
internal fun CatalogRoute(
    onBackClick: () -> Unit,
    onProductClick: (Int) -> Unit,
    productType: String,
    modifier: Modifier = Modifier,
) {
    val viewModel = hiltViewModel<CatalogViewModel>()

    LaunchedEffect(Unit) {
        viewModel.getCategoryMetadata(productType)
    }

    val productList = viewModel.getProducts(productType).collectAsLazyPagingItems()
    val state by viewModel.collectAsState()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    when (uiState) {
        is BaseUIState.Error -> {}
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
                onBackClick = onBackClick,
                onFilterApplied = viewModel::setCatalogFilters,
                onSortApplied = viewModel::setCatalogSortType,
                onProductClick = onProductClick,
                currentCatalogFilter = state.catalogFilter!!,
                currentSortType = state.catalogSort,
                productFeedModelList = productList,
            )
        }
    }

}

@Composable
private fun CatalogScreen(
    onBackClick: () -> Unit,
    onFilterApplied: (CatalogFilter) -> Unit,
    onSortApplied: (CatalogSortType) -> Unit,
    onProductClick: (Int) -> Unit,
    currentCatalogFilter: CatalogFilter,
    currentSortType: CatalogSortType,
    productFeedModelList: LazyPagingItems<ProductFeedModel>,
    modifier: Modifier = Modifier,
) {
    val staggeredGridState = rememberLazyStaggeredGridState()
    var isAppBarVisible by remember { mutableStateOf(true) }

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

    var filterVisibility by remember {
        mutableStateOf(false)
    }

    var sortBottomSheetVisibility by remember {
        mutableStateOf(false)
    }

    BackHandler {
        if (filterVisibility) filterVisibility = false
        else onBackClick()
    }

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
                        if (filterVisibility) filterVisibility = false
                    },
                    filterVisibility = filterVisibility,
                    currentCatalogFilter = currentCatalogFilter,
                )
            }
        },
        bottomBar = {
            if (sortBottomSheetVisibility) {
                CatalogSortBottomSheet(
                    onSortTypeChange = { newSortType ->
                        onSortApplied(newSortType)
                        sortBottomSheetVisibility = false
                    },
                    currentSortType = currentSortType,
                )
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
        ) {
            ProductFeed(
                onProductClick = onProductClick,
                productFeedModelList = productFeedModelList,
                scrollState = staggeredGridState,
            )
        }
    }
}