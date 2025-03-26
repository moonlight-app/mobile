# Description
Android application for jewelry store Moonlight.

# Overview
### Image
|                 **Sign in**<br>![Sign in](/assets/img/sign_in.png)                  | **Sign up**<br>![Sign up](/assets/img/registration.png) | **Profile**<br>![Profile](/assets/img/profile.png) | **Favorites**<br>![Favorites](/assets/img/favorites.png) |                 **Cart**<br>![Cart](/assets/img/cart.png)                  |  
|:-----------------------------------------------------------------------------------:|:-------------------------------------------------------:|:--------------------------------------------------:|:--------------------------------------------------------:|:--------------------------------------------------------------------------:|
| **Catalog categories**<br>![Catalog categories](/assets/img/catalog_categories.png) |   **Catalog**<br>![Catalog](/assets/img/catalog.png)    | **Filters**<br>![Filters](/assets/img/filters.png) |        **Sort**<br>![Sort](/assets/img/sort.png)         | **Product details**<br>![Product details](/assets/img/product_details.png) |

### Gif
| Registration                      | Catalog & Create order & Favorites           | Filters & sort                    | Favorites                      |
|-----------------------------------|----------------------------------------------|-----------------------------------|--------------------------------|
| ![](/assets/gif/registration.gif) | ![](/assets/gif/catalog_order_favorites.gif) | ![](/assets/gif/filters_sort.gif) | ![](/assets/gif/favorites.gif) |

# Built With 🛠
- [Multi-Module Architecture](https://developer.android.com/topic/modularization)
- [Clean Architecture](https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html) 
- [MVI and MVVM]() Patterns for presentation layer.
- [MVI-Orbit](https://orbit-mvi.org/) MVI framework for Android.
- [Compose](https://developer.android.com/compose) Toolkit for building native UI (in a declarative way - 100% Kotlin).
- [Paging 3](https://developer.android.com/topic/libraries/architecture/paging/v3-overview) Pagination library for Android.
- [Shimmer](https://github.com/valentinilk/compose-shimmer) Loading animation library supported by Jetpack Compose.
- [Coil](https://github.com/coil-kt/coil) Image downloading and caching library supported by Jetpack Compose.
- [Jetpack Navigation](https://developer.android.com/guide/navigation) Navigation library for Jetpack Compose.
- [Kotlin-Coroutines](https://github.com/Kotlin/kotlinx.coroutines) Library support for Kotlin coroutines.
- [Flow](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/-flow/) Stream processing API, built on top of Coroutines.
- [Dagger Hilt](https://dagger.dev/hilt/) Dependency injection library for Android.
- [Retrofit](https://github.com/square/retrofit) Type-safe REST client for Android to consume RESTful web services.
- [OkHttp3](https://square.github.io/okhttp/) HTTP client for Android.
- [kotlinx.serialization](https://github.com/Kotlin/kotlinx.serialization) - Kotlin multiplatform / multi-format reflectionless serialization.
- [Room](https://developer.android.com/training/data-storage/room) Database for Android.

# Module Design
> [!NOTE]
> Nearly all modules are organized into api and impl folders. The api folder contains what the module exposes externally, while the impl folder holds the implementation details.
```
└── module
    ├── api/ ← Public interfaces
    ├── impl/ ← Implementation classes (with internal modifier)
```

## Application
| Module name                          | Type                | Description                                        |
|--------------------------------------|---------------------|----------------------------------------------------|
| [app](/app/)                         | Android Application | Application setup. Navigation, Hilt setup.         |

## Core
| Module name                          | Type            | Description                                                                                                                                                                                                                                              |
|--------------------------------------|-----------------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| [core](/core/)                       | -               | This is the core component of the application, integrating key business rules, infrastructure, and logic. It ensures independence from external layers (UI, frameworks) and serves as the foundation for scalability, testability, and code reusability. |
| [core-common](/core/common/)         | Android Library | A module which contains shared logic.                                                                                                                                                                                                                    |
| [core-ui](/core-ui/)                 | Android Library | A module which contains Moonlight design system.                                                                                                                                                                                                         |
| [core-data](/core/data/)             | Android Library | A module integrating data sources, database and services to provide repositories.                                                                                                                                                                        |
| [core-network](/core-network/)       | Android Library | A module which provides interaction with the Internet (Services).                                                                                                                                                                                        |
| [core-datasource](/core/datasource/) | Android Library | A module which contains datasource for saving local data.                                                                                                                                                                                                |
| [core-database](/core/database/)     | Android Library | A module which contains Database for caching.                                                                                                                                                                                                            |
| [core-domain](/core/domain/)         | Android Library | A module which contains business rules.                                                                                                                                                                                                                  |
| [core-model](/core/domain-model/)    | Kotlin Library  | A module which contains domain models.                                                                                                                                                                                                                   |

## Feature
### Authorization
| Module name                                                            | Type            | Description                  |
|------------------------------------------------------------------------|-----------------|------------------------------|
| [feature-auth-signin](/feature-auth-signin/)                           | Android Library | Sign in screen.              |
| [feature-auth-signup-registration](/feature-auth-signup-registration/) | Android Library | Registration screen.         |
| [feature-auth-signup-confirmcode](/feature-auth-signup-confirmcode/)   | Android Library | Confirm code screen.         |
| [feature-auth-signup-complete](/feature-auth-signup-complete/)         | Android Library | Registration compete screen. |

### Catalog
| Module name                                                | Type            | Description                             |
|------------------------------------------------------------|-----------------|-----------------------------------------|
| [feature-catalog-categories](/feature-catalog-categories/) | Android Library | Main screen. Screen for chose category. |
| [feature-catalog](/feature-catalog/)                       | Android Library | Catalog screen.                         |
| [feature-catalog-filter](/feature-catalog-filter/)         | Android Library | Filters for catalog.                    |
| [feature-catalog-sort](/feature-catalog-sort/)             | Android Library | Sort for catalog.                       |
| [feature-catalog-product](/feature-catalog-product/)       | Android Library | Product details screen.                 |

### Cart
| Module name                    | Type            | Description  |
|--------------------------------|-----------------|--------------|
| [feature-cart](/feature-cart/) | Android Library | Cart screen. |

### Profile
| Module name                                                        | Type            | Description             |
|--------------------------------------------------------------------|-----------------|-------------------------|
| [feature-profile](/feature-profile/)                               | Android Library | Profile screen.         |
| [feature-profile-changepassword](/feature-profile-changepassword/) | Android Library | Change password screen. |
| [feature-profile-edit](/feature-profile-edit/)                     | Android Library | Edit profile screen.    |

### Favorites
| Module name                              | Type            | Description       |
|------------------------------------------|-----------------|-------------------|
| [feature-favorites](/feature-favorites/) | Android Library | Favorites screen. |

### Order
| Module name                      | Type             | Description   |
|----------------------------------|------------------|---------------|
| [feature-order](/feature-order/) | Android Library  | Order screen. |
