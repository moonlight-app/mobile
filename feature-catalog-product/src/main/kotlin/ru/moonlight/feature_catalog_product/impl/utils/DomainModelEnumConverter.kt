package ru.moonlight.feature_catalog_product.impl.utils

import android.content.Context
import ru.moonlight.feature_catalog_product.R

internal fun String.convertEnumName(context: Context) =
    when (this.uppercase()) {
        "GOLD" -> context.getString(R.string.gold)
        "SILVER" -> context.getString(R.string.silver)
        "PLATINUM" -> context.getString(R.string.platinum)
        "WHITE_GOLD" -> context.getString(R.string.whiteGold)
        "PINK_GOLD" -> context.getString(R.string.pinkGold)
        "CERAMIC" -> context.getString(R.string.ceramic)
        "NOTHING" -> context.getString(R.string.nothing)
        "DIAMOND" -> context.getString(R.string.brilliant)
        "SAPPHIRE" -> context.getString(R.string.sapphire)
        "PEARL" -> context.getString(R.string.pearl)
        "AMETHYST" -> context.getString(R.string.amethyst)
        "FIANIT" -> context.getString(R.string.cubicZirconia)
        "EMERALD" -> context.getString(R.string.emerald)
        "RUBY" -> context.getString(R.string.ruby)
        else -> "-"
    }