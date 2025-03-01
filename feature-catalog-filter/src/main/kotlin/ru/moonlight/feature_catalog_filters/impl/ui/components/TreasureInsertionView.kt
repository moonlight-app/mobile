package ru.moonlight.feature_catalog_filters.impl.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import ru.moonlight.api.component.CardWithTitleAndGrid
import ru.moonlight.api.component.CheckBoxWithTextComponent
import ru.moonlight.feature_catalog_filters.R
import ru.moonlight.feature_catalog_filters.api.TreasureInsert
import ru.moonlight.feature_catalog_filters.api.translateName

@Composable
internal fun TreasureInsertionView(
    onInsertionClick: (TreasureInsert) -> Unit,
    choosenInsertion: List<TreasureInsert>,
    treasureInserts: List<TreasureInsert> = TreasureInsert.entries.toList(),
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current

    CardWithTitleAndGrid(
        title = stringResource(R.string.insertion),
        cellsSize = 2,
        list = treasureInserts,
        modifier = modifier,
    ) { insertion ->
        CheckBoxWithTextComponent(
            text = insertion.translateName(context),
            checked = choosenInsertion.contains(insertion),
            onCheckedChange = { onInsertionClick(insertion) },
        )
    }
}