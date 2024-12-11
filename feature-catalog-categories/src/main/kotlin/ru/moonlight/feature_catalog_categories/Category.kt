package ru.moonlight.feature_catalog_categories

import android.content.Context
import androidx.annotation.DrawableRes

internal class Category(
    val title: String,
    val productType: String,
    @DrawableRes val image: Int,
)

internal val Context.categories: List<Category>
    get() = listOf(
        Category(this.getString(R.string.ring), "ring", R.drawable.ring),
        Category(this.getString(R.string.earrings), "earrings", R.drawable.earrings),
        Category(this.getString(R.string.watch), "watch", R.drawable.watch),
        Category(this.getString(R.string.bracelet), "bracelet", R.drawable.bracelet),
        Category(this.getString(R.string.necklace), "necklace", R.drawable.necklace),
        Category(this.getString(R.string.chain), "chain", R.drawable.chain),
    )