package ru.moonlight.feature_catalog_filters.api

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import ru.moonlight.feature_catalog_filters.impl.ui.components.AudienceView
import ru.moonlight.feature_catalog_filters.impl.ui.components.ButtonsView
import ru.moonlight.feature_catalog_filters.impl.ui.components.MaterialsView
import ru.moonlight.feature_catalog_filters.impl.ui.components.PriceView
import ru.moonlight.feature_catalog_filters.impl.ui.components.ProductSizeView
import ru.moonlight.feature_catalog_filters.impl.ui.components.TreasureInsertionView
import ru.moonlight.api.theme.MoonlightTheme

@Composable
fun CatalogFiltersScreen(
    filters: CatalogFilter,
    onFiltersApplied: (CatalogFilter) -> Unit,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()

    var minPrice by remember {
        mutableStateOf(filters.minPrice)
    }

    var maxPrice by remember {
        mutableStateOf(filters.maxPrice)
    }

    var choosenSizes by remember {
        mutableStateOf(filters.size)
    }

    var choosenMaterials by remember {
        mutableStateOf(filters.material)
    }

    var choosenAudience by remember {
        mutableStateOf(filters.forWhom)
    }

    var choosenInserts by remember {
        mutableStateOf(filters.treasureInsert)
    }

    var heightForScroll by remember { mutableStateOf(0.dp) }
    val density = LocalDensity.current

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(horizontal = MoonlightTheme.dimens.paddingBetweenComponentsHorizontal),
        verticalArrangement = Arrangement.spacedBy(
            space = MoonlightTheme.dimens.paddingBetweenComponentsBigVertical,
        )
    ) {
        PriceView(
            onMinPriceChange = { minPrice = it },
            onMaxPriceChange = { maxPrice = it },
            defaultMinPrice = filters.defaultMinPrice,
            minPrice = minPrice,
            defaultMaxPrice = filters.defaultMaxPrice,
            maxPrice = maxPrice,
        )
        ProductSizeView(
            onSizeClick = { size ->
                choosenSizes = if (choosenSizes.contains(size)) choosenSizes.minus(size)
                else choosenSizes.plus(size)
            },
            sizes = filters.defaultSize,
            chosenSizes = choosenSizes,
        )
        MaterialsView(
            onMaterialClick = {
                choosenMaterials = if (choosenMaterials.contains(it)) choosenMaterials.minus(it)
                else choosenMaterials.plus(it) },
            chosenMaterials = choosenMaterials,
        )
        AudienceView(
            onAudienceClick = {
                choosenAudience = if (choosenAudience.contains(it)) choosenAudience.minus(it)
                else choosenAudience.plus(it)
            },
            choosenAudience = choosenAudience,
        )
        TreasureInsertionView(
            onInsertionClick = { insertion ->
                choosenInserts = if (choosenInserts.contains(insertion)) choosenInserts.minus(insertion)
                else choosenInserts.plus(insertion)
            },
            choosenInsertion = choosenInserts,
        )
        Spacer(
            Modifier
                .fillMaxWidth()
                .height(heightForScroll + MoonlightTheme.dimens.paddingBetweenComponentsSmallVertical)
        )

    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .imePadding(),
        contentAlignment = Alignment.BottomCenter
    ) {
        ButtonsView(
            modifier = Modifier
                .onGloballyPositioned { layoutCoordinates: LayoutCoordinates ->
                    with(density) {
                        heightForScroll = layoutCoordinates.size.height.toDp()
                    }
                },
            onClearClick = {
                minPrice = filters.defaultMinPrice
                maxPrice = filters.defaultMaxPrice
                choosenSizes = emptyList()
                choosenMaterials = emptyList()
                choosenAudience = emptyList()
                choosenInserts = emptyList()
            },
            onApplyClick = {
                onFiltersApplied(
                    CatalogFilter(
                        defaultMinPrice = filters.defaultMinPrice,
                        defaultMaxPrice = filters.defaultMaxPrice,
                        defaultSize = filters.defaultSize,
                        minPrice = if (minPrice.substringBefore(".") == "0") filters.minPrice else minPrice,
                        maxPrice = if (maxPrice.substringBefore(".") == "0") filters.maxPrice else maxPrice,
                        size = choosenSizes,
                        material = choosenMaterials,
                        forWhom = choosenAudience,
                        treasureInsert = choosenInserts,
                    )
                )
            },
            applyEnable = (
                minPrice != filters.minPrice ||
                maxPrice != filters.maxPrice ||
                choosenSizes != filters.size ||
                choosenMaterials != filters.material ||
                choosenAudience != filters.forWhom ||
                choosenInserts != filters.treasureInsert
            )
        )
    }
}

