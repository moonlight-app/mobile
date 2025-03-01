package ru.moonlight.feature_favourites.impl.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.moonlight.api.component.FavoriteProductFeedComponent
import ru.moonlight.api.component.FavoriteProductFeedModel
import ru.moonlight.api.theme.MoonlightTheme
import ru.moonlight.feature_favourites.impl.presentation.FavouritesAction
import ru.moonlight.feature_favourites.impl.presentation.FavouritesViewModel
import ru.moonlight.feature_favourites.impl.ui.component.FavoriteTopAppBar

@Composable
fun FavouritesRoute(
    onBackClick: () -> Unit,
    onProductClick: (Long) -> Unit,
    onVisitCatalogClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val viewModel: FavouritesViewModel = hiltViewModel()
    val orders = viewModel.orders.collectAsLazyPagingItems()

    if (orders.loadState.refresh is LoadState.Loading) {
        LaunchedEffect(Unit) {
            viewModel.dispatch(FavouritesAction.LoadData())
        }
    }

    FavouritesScreen(
        onBackClick = onBackClick,
        onProductClick = onProductClick,
        onVisitCatalogClick = onVisitCatalogClick,
        onFavoriteClick = { id, status -> viewModel.dispatch(FavouritesAction.ChangeProductFavoriteStatus(id = id, status = !status)) },
        favoriteProducts = orders,
        modifier = modifier,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun FavouritesScreen(
    onBackClick:() -> Unit,
    onProductClick:(Long) -> Unit,
    onVisitCatalogClick:() -> Unit,
    onFavoriteClick: (id: Long, currentStatus: Boolean) -> Unit,
    favoriteProducts: LazyPagingItems<FavoriteProductFeedModel>,
    modifier: Modifier = Modifier
) {
    val coroutineScope = rememberCoroutineScope()
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
                favoriteProducts.refresh()
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
                FavoriteTopAppBar(
                    onBackClick = onBackClick,
                )
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                FavoriteProductFeedComponent(
                    onProductClick = onProductClick,
                    onFavouriteClick = onFavoriteClick,
                    onVisitCatalogClick = onVisitCatalogClick,
                    productFeedModelList = favoriteProducts,
                    scrollState = rememberLazyStaggeredGridState(),
                    isFavoriteButtonVisible = true,
                )
            }
        }
    }
}