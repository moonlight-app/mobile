package ru.moonlight.feature_catalog_product.impl.ui.component

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import ru.moonlight.api.component.CardWithTitleAndGrid
import ru.moonlight.api.theme.MoonlightTheme
import ru.moonlight.api.widget.text.DescriptionTextWidget
import ru.moonlight.api.widget.text.SecondDescriptionTextWidget
import ru.moonlight.feature_catalog_product.R
import ru.moonlight.feature_catalog_product.impl.utils.convertEnumName

@Composable
internal fun ParametrsCard(
    material: String,
    standard: String,
    typeOfStandard: String,
    insertion: String,
    weight: String,
    article: String,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current

    CardWithTitleAndGrid(
        modifier = modifier
            .background(
                color = MoonlightTheme.colors.card2,
                shape = MoonlightTheme.shapes.buttonShape,
            )
            .padding(MoonlightTheme.dimens.paddingBetweenComponentsSmallVertical),
        title = stringResource(R.string.product_params),
        cellsSize = 2,
        map = getMap(
            context = context,
            material = material.convertEnumName(context),
            standard = standard,
            typeOfStandard = typeOfStandard,
            insertion = insertion.convertEnumName(context),
            weight = weight,
            article = article,
        ),
        item1 = { DescriptionTextWidget(text = it) },
        item2 = { DescriptionTextWidget(text = it) },
        footer = {
            SecondDescriptionTextWidget(
                text = stringResource(R.string.small_description),
                textColor = MoonlightTheme.colors.hintText,
            )
        }
    )
}

private fun getMap(
    context: Context,
    material: String,
    standard: String,
    typeOfStandard: String,
    insertion: String,
    weight: String,
    article: String,

): Map<String, String> =
    mapOf(
        context.getString(R.string.material_of_product) to material,
        context.getString(R.string.standard) to standard,
        context.getString(R.string.type_of_standard) to typeOfStandard,
        context.getString(R.string.insertion) to insertion,
        context.getString(R.string.weight_of_product) to weight,
        context.getString(R.string.article) to article,
    )