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
import ru.moonlight.common.base.BaseUIState
import ru.moonlight.domain_model.catalog.CatalogProductDomainModel
import ru.moonlight.feature_catalog.impl.presentation.CatalogViewModel
import ru.moonlight.feature_catalog.impl.ui.component.CatalogSortBottomSheet
import ru.moonlight.feature_catalog.impl.ui.component.ProductFeed
import ru.moonlight.feature_catalog.impl.ui.component.TopAppBar
import ru.moonlight.feature_catalog_filters.api.CatalogFilter
import ru.moonlight.feature_catalog_sort.api.CatalogSortType
import ru.moonlight.theme.MoonlightTheme
import ru.moonlight.ui.ProgressBarComponent
import ru.moonlight.utils.animation.TopBarInOutAnimation

@Composable
internal fun CatalogScreen(
    onBackClick: () -> Unit,
    onProductClick: (Int) -> Unit,
    productType: String,
    modifier: Modifier = Modifier,
) {
    val viewModel = hiltViewModel<CatalogViewModel>()

    LaunchedEffect(Unit) {
        viewModel.getMetadata(productType)
    }


    val productList = viewModel.getPagingData(productType).collectAsLazyPagingItems()
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
                ProgressBarComponent()
            }
        }
        BaseUIState.Success -> {
            CatalogView(
                modifier = modifier,
                onBackClick = onBackClick,
                onFilterApplied = viewModel::setFilters,
                onSortApplied = viewModel::setSortType,
                onProductClick = onProductClick,
                currentCatalogFilter = state.catalogFilter!!,
                currentSortType = state.catalogSort,
                productList = productList,
            )
        }
    }

}

@Composable
private fun CatalogView(
    onBackClick: () -> Unit,
    onFilterApplied: (CatalogFilter) -> Unit,
    onSortApplied: (CatalogSortType) -> Unit,
    onProductClick: (Int) -> Unit,
    currentCatalogFilter: CatalogFilter,
    currentSortType: CatalogSortType,
    productList: LazyPagingItems<CatalogProductDomainModel>,
    modifier: Modifier = Modifier,
) {
    val staggeredGridState = rememberLazyStaggeredGridState()
    var isAppBarVisible by remember { mutableStateOf(true) }

    LaunchedEffect(staggeredGridState, productList) {
        var lastScrollOffset = 0
        var lastVisibleIndex = 0

        snapshotFlow {
            staggeredGridState.firstVisibleItemIndex to staggeredGridState.firstVisibleItemScrollOffset
        }.collect { (currentIndex, currentScrollOffset) ->
            val isLoading = productList.loadState.append is LoadState.Loading ||
                    productList.loadState.refresh is LoadState.Loading

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
                productList = productList,
                scrollState = staggeredGridState,
            )
        }
    }
}