package ru.moonlight.feature_catalog.impl.ui.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ru.moonlight.feature_catalog_sort.api.CatalogSortType
import ru.moonlight.feature_catalog_sort.api.SortBottomSheet

@Composable
internal fun CatalogSortBottomSheet(
    onSortTypeChange: (CatalogSortType) -> Unit,
    currentSortType: CatalogSortType,
    modifier: Modifier = Modifier,
) {
    SortBottomSheet(
        modifier = modifier,
        currentSortType = currentSortType,
        onSortSelected = onSortTypeChange,
    )
}
