package ru.moonlight.feature_catalog_filters.impl.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import ru.moonlight.api.component.CardWithTitleAndGrid
import ru.moonlight.api.widget.button.ButtonChipWidget
import ru.moonlight.feature_catalog_filters.R
import ru.moonlight.feature_catalog_filters.api.Audience
import ru.moonlight.feature_catalog_filters.api.translateName

@Composable
internal fun AudienceView(
    onAudienceClick: (Audience) -> Unit,
    choosenAudience: List<Audience>,
    modifier: Modifier = Modifier,
    audiences: List<Audience> = Audience.entries.toList()
) {
    val context = LocalContext.current

    CardWithTitleAndGrid(
        modifier = modifier,
        title = stringResource(R.string.forWhom),
        cellsSize = 2,
        list = audiences,
    ) { audience ->
         ButtonChipWidget(
             onClick = { onAudienceClick(audience) },
             text = audience.translateName(context),
             selected = audience in choosenAudience,
         )
    }
}