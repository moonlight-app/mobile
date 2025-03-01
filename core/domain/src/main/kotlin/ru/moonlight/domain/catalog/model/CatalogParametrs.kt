package ru.moonlight.domain.catalog.model

data class CatalogParametersDomainModel(
    val productType: String,
    val sortBy: SortByDomain?,
    val minPrice: Float?,
    val maxPrice: Float?,
    val sizes: List<Float>?,
    val audiences: List<AudienceDomain>?,
    val materials: List<MaterialDomain>?,
    val treasures: List<TreasureDomain>?,
)

/* sort model from backend
* https://github.com/moonlight-app/backend/blob/main/src/main/java/ru/moonlightapp/backend/model/attribute/CatalogSorting.java
*/
enum class SortByDomain {
    POPULARITY,
    PRICE_ASC,
    PRICE_DESC,
}

/* audience model from backend
* https://github.com/moonlight-app/backend/blob/main/src/main/java/ru/moonlightapp/backend/model/attribute/Audience.java
*/
enum class AudienceDomain {
    WOMAN,
    MAN,
    CHILDREN,
    UNISEX;
}

/* material model from backend
 * https://github.com/moonlight-app/backend/blob/main/src/main/java/ru/moonlightapp/backend/model/attribute/Material.java
 */
enum class MaterialDomain {
    GOLD,
    SILVER,
    PLATINUM,
    WHITE_GOLD,
    PINK_GOLD,
    CERAMIC;
}

/* treasure model from backend
* https://github.com/moonlight-app/backend/blob/main/src/main/java/ru/moonlightapp/backend/model/attribute/Treasure.java
*/
enum class TreasureDomain {
    NOTHING,
    DIAMOND,
    SAPPHIRE,
    PEARL,
    AMETHYST,
    FIANIT,
    EMERALD,
    RUBY;
}
