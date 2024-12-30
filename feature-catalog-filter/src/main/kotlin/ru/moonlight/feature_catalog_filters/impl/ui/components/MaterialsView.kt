package ru.moonlight.feature_catalog_filters.impl.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import ru.moonlight.feature_catalog_filters.R
import ru.moonlight.feature_catalog_filters.api.Material
import ru.moonlight.feature_catalog_filters.api.translateName
import ru.moonlight.ui.CheckBoxWithTextComponent

@Composable
internal fun MaterialsView(
    onMaterialClick: (Material) -> Unit,
    chosenMaterials: List<Material>,
    materials: List<Material> = Material.entries.toList(),
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current

    FilterCard(
        title = stringResource(R.string.materials),
        cellsSize = 2,
        list = materials,
        modifier = modifier
    ) { material ->
        CheckBoxWithTextComponent(
            text = material.translateName(context),
            checked = chosenMaterials.contains(material),
            onCheckedChange = { onMaterialClick(material) },
        )
    }
}