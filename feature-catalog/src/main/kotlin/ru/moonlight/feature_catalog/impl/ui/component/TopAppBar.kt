package ru.moonlight.feature_catalog.impl.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import ru.moonlight.api.component.TopAppBarComponent
import ru.moonlight.api.theme.MoonlightTheme
import ru.moonlight.api.widget.text.SubTitleTextWidget
import ru.moonlight.feature_catalog.R
import ru.moonlight.feature_catalog_filters.api.CatalogFilter
import ru.moonlight.feature_catalog_filters.api.CatalogFiltersScreen

@Composable
internal fun TopAppBar(
    onBackClick: () -> Unit,
    onActionClick: () -> Unit,
    onFilterApplied: (CatalogFilter) -> Unit,
    onSortClick: () -> Unit,
    filterVisibility: Boolean,
    currentCatalogFilter: CatalogFilter,
    countOfItems: Int,
    modifier: Modifier = Modifier,
) {
    TopAppBarComponent(
        modifier = modifier,
        title = stringResource(R.string.catalog),
        onBackClick = onBackClick,
        actionText = stringResource(R.string.filter),
        onActionClick = onActionClick,
        actionVisibility = filterVisibility,
        contentUnderTitle = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = MoonlightTheme.dimens.paddingBetweenComponentsSmallVertical),
                    contentAlignment = Alignment.CenterStart
                ) {
                    Icon(
                        modifier = Modifier
                            .padding(start = MoonlightTheme.dimens.paddingFromEdges)
                            .clickable {
                                onSortClick()
                            },
                        painter = painterResource(R.drawable.sort),
                        tint = MoonlightTheme.colors.text,
                        contentDescription = "sort",
                    )
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .matchParentSize()
                        .padding(bottom = MoonlightTheme.dimens.paddingBetweenComponentsSmallVertical),
                    contentAlignment = Alignment.Center
                ) {
                    SubTitleTextWidget(
                        "$countOfItems ${ if (countOfItems == 1) stringResource(R.string.item) else stringResource(R.string.items) }",
                    )
                }
            }
        },
        content = {
            CatalogFiltersScreen(
                onFiltersApplied = onFilterApplied,
                filters = currentCatalogFilter,
            )
        },
    )
}