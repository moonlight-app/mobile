package ru.moonlight.feature_catalog_sort.api

import android.content.Context
import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import ru.moonlight.feature_catalog_sort.R

@Parcelize
enum class CatalogSortType : Parcelable {
    POPULARITY,
    INCREASE_PRICE,
    DECREASE_PRICE;
}

fun CatalogSortType.translateName(context: Context): String {
    return when (this) {
        CatalogSortType.POPULARITY -> context.getString(R.string.popularity)
        CatalogSortType.INCREASE_PRICE -> context.getString(R.string.increasePrice)
        CatalogSortType.DECREASE_PRICE -> context.getString(R.string.decreasePrice)
    }
}

fun CatalogSortType.getSortIcon(): Int {
    return when (this) {
        CatalogSortType.POPULARITY -> R.drawable.popularity
        CatalogSortType.INCREASE_PRICE -> R.drawable.increase_price
        CatalogSortType.DECREASE_PRICE -> R.drawable.decrease_price
    }
}