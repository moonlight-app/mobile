package ru.moonlight.feature_catalog_filters.api

import android.content.Context
import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import ru.moonlight.feature_catalog_filters.R

@Parcelize
data class CatalogFilter(
    val defaultMinPrice: String,
    val defaultMaxPrice: String,
    val defaultSize: List<Float>,
    var minPrice: String = defaultMinPrice,
    var maxPrice: String = defaultMaxPrice,
    var size: List<Float> = emptyList(),
    var material: List<Material> = emptyList(),
    var forWhom: List<Audience> = emptyList(),
    var treasureInsert: List<TreasureInsert> = emptyList(),
): Parcelable

enum class Material {
    GOLD,
    SILVER,
    PLATINUM,
    WHITE_GOLD,
    PINK_GOLD,
    CERAMIC;
}

fun Material.translateName(context: Context): String = when (this) {
    Material.GOLD -> context.getString(R.string.gold)
    Material.SILVER -> context.getString(R.string.silver)
    Material.PLATINUM -> context.getString(R.string.platinum)
    Material.WHITE_GOLD -> context.getString(R.string.whiteGold)
    Material.PINK_GOLD -> context.getString(R.string.pinkGold)
    Material.CERAMIC -> context.getString(R.string.ceramic)
}

enum class Audience {
    WOMAN,
    MAN,
    CHILDREN,
    UNISEX;
}

fun Audience.translateName(context: Context): String = when (this) {
    Audience.WOMAN -> context.getString(R.string.woman)
    Audience.MAN -> context.getString(R.string.man)
    Audience.CHILDREN -> context.getString(R.string.children)
    Audience.UNISEX -> context.getString(R.string.unisex)
}

enum class TreasureInsert {
    Brilliant,
    Sapphire,
    PEARL,
    AMETHYST,
    CUBIC_ZIRCONIA,
    EMERALD,
    RUBY,
    NOTHING;
}

fun TreasureInsert.translateName(context: Context): String = when (this) {
    TreasureInsert.Brilliant -> context.getString(R.string.brilliant)
    TreasureInsert.Sapphire -> context.getString(R.string.sapphire)
    TreasureInsert.PEARL -> context.getString(R.string.pearl)
    TreasureInsert.AMETHYST -> context.getString(R.string.amethyst)
    TreasureInsert.CUBIC_ZIRCONIA -> context.getString(R.string.cubicZirconia)
    TreasureInsert.EMERALD -> context.getString(R.string.emerald)
    TreasureInsert.RUBY -> context.getString(R.string.ruby)
    TreasureInsert.NOTHING -> context.getString(R.string.nothing)
}