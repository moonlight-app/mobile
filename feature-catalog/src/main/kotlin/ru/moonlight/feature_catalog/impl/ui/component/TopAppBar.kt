package ru.moonlight.feature_catalog.impl.ui.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import ru.moonlight.feature_catalog.R
import ru.moonlight.feature_catalog_filters.api.CatalogFilter
import ru.moonlight.feature_catalog_filters.api.CatalogFiltersScreen
import ru.moonlight.api.component.TopAppBarComponent

@Composable
internal fun TopAppBar(
    onBackClick: () -> Unit,
    onActionClick: () -> Unit,
    filterVisibility: Boolean,
    onFilterApplied: (CatalogFilter) -> Unit,
    currentCatalogFilter: CatalogFilter,
    modifier: Modifier = Modifier,
) {
    TopAppBarComponent(
        modifier = modifier,
        title = stringResource(R.string.catalog),
        onBackClick = onBackClick,
        actionText = stringResource(R.string.filter),
        onActionClick = onActionClick,
        actionVisibility = filterVisibility,
        content = {
            CatalogFiltersScreen(
                onFiltersApplied = onFilterApplied,
                filters = currentCatalogFilter,
            )
        },
    )
}