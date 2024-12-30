package ru.moonlight.feature_catalog_sort.api

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.launch
import ru.moonlight.feature_catalog_sort.impl.ui.SortButton
import ru.moonlight.feature_catalog_sort.impl.ui.Title
import ru.moonlight.theme.MoonlightTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SortBottomSheet(
    onSortSelected: (CatalogSortType) -> Unit,
    currentSortType: CatalogSortType,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current

    val scope = rememberCoroutineScope()
    val bottomSheetState = rememberModalBottomSheetState()

    var sortType by remember {
        mutableStateOf(currentSortType)
    }

    ModalBottomSheet (
        modifier = modifier,
        onDismissRequest = {
            onSortSelected(sortType)
        },
        sheetState = bottomSheetState,
        containerColor = MoonlightTheme.colors.background,
        contentColor = MoonlightTheme.colors.text,
        scrimColor = Color.Black.copy().copy(alpha = 0.7f),
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = MoonlightTheme.dimens.paddingBetweenComponentsHorizontal),
        ) {
            Title()
            CatalogSortType.entries.forEach { type ->
                SortButton(
                    onSortSelected = {
                        onSortSelected(type)
                        scope.launch { bottomSheetState.hide() }
                        sortType = type
                    },
                    text = type.translateName(context),
                    icon = type.getSortIcon(),
                    isSelected = (type == sortType),

                )
            }
        }
    }
}