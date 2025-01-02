package ru.moonlight.feature_catalog_categories.impl

import android.content.Context
import androidx.annotation.DrawableRes
import ru.moonlight.feature_catalog_categories.R

internal class Category(
    val title: String,
    val productType: String,
    @DrawableRes val image: Int,
)

internal val Context.categories: List<Category>
    get() = listOf(
        Category(this.getString(R.string.ring), "ring", ru.moonlight.ui.R.drawable.ring),
        Category(this.getString(R.string.earrings), "earrings", ru.moonlight.ui.R.drawable.earrings),
        Category(this.getString(R.string.watch), "watch", ru.moonlight.ui.R.drawable.watch),
        Category(this.getString(R.string.bracelet), "bracelet", ru.moonlight.ui.R.drawable.bracelet),
        Category(this.getString(R.string.necklace), "necklace", ru.moonlight.ui.R.drawable.necklace),
        Category(this.getString(R.string.chain), "chain", ru.moonlight.ui.R.drawable.chain),
    )