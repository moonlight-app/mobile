# Description
Android application for jewelry store Moonlight.

# Overview
### Image
<div align="center" style="display: flex; gap: 20px; justify-content: center; flex-wrap: wrap;">
  <div style="display: flex; flex-direction: column; align-items: center;">
    <span style="margin-bottom: 8px; font-weight: bold;">Sign in</span>
    <img src="/assets/img/sign_in.png" width="250" alt="Sign in"/>
  </div>

  <div style="display: flex; flex-direction: column; align-items: center;">
    <span style="margin-bottom: 8px; font-weight: bold;">Sign up</span>
    <img src="/assets/img/registration.png" width="250" alt="Sign up"/>
  </div>

  <div style="display: flex; flex-direction: column; align-items: center;">
    <span style="margin-bottom: 8px; font-weight: bold;">Profile</span>
    <img src="/assets/img/profile.png" width="250" alt="Profile"/>
  </div>

  <div style="display: flex; flex-direction: column; align-items: center;">
    <span style="margin-bottom: 8px; font-weight: bold;">Favorites</span>
    <img src="/assets/img/favorites.png" width="250" alt="Favorites"/>
  </div>

  <div style="display: flex; flex-direction: column; align-items: center;">
    <span style="margin-bottom: 8px; font-weight: bold;">Catalog categories</span>
    <img src="/assets/img/catalog_categories.png" width="250" alt="Catalog categories"/>
  </div>

  <div style="display: flex; flex-direction: column; align-items: center;">
    <span style="margin-bottom: 8px; font-weight: bold;">Catalog</span>
    <img src="/assets/img/catalog.png" width="250" alt="Catalog"/>
  </div>

  <div style="display: flex; flex-direction: column; align-items: center;">
    <span style="margin-bottom: 8px; font-weight: bold;">Filters</span>
    <img src="/assets/img/filters.png" width="250" alt="Filters"/>
  </div>

  <div style="display: flex; flex-direction: column; align-items: center;">
    <span style="margin-bottom: 8px; font-weight: bold;">Sort</span>
    <img src="/assets/img/sort.png" width="250" alt="Sort"/>
  </div>

  <div style="display: flex; flex-direction: column; align-items: center;">
    <span style="margin-bottom: 8px; font-weight: bold;">Product details</span>
    <img src="/assets/img/product_details.png" width="250" alt="Product details"/>
  </div>

  <div style="display: flex; flex-direction: column; align-items: center;">
    <span style="margin-bottom: 8px; font-weight: bold;">Cart</span>
    <img src="/assets/img/cart.png" width="250" alt="Cart"/>
  </div>
</div>

### Gif
<div align="center" style="display: flex; gap: 20px; justify-content: center; flex-wrap: wrap; ">
  <div style="display: flex; flex-direction: column; align-items: center;">
    <span style="margin-bottom: 8px; font-weight: bold;">Registration</span>
    <img src="/assets/gif/registration.gif" width="250" alt="Registration"/>
  </div>

  <div style="display: flex; flex-direction: column; align-items: center;">
    <span style="margin-bottom: 8px; font-weight: bold;">Catalog & Create order & Favorites</span>
    <img src="/assets/gif/catalog_order_favorites.gif" width="250" alt="Catalog & Create order & Favorites"/>
  </div>

  <div style="display: flex; flex-direction: column; align-items: center;">
    <span style="margin-bottom: 8px; font-weight: bold;">Filters & sort</span>
    <img src="/assets/gif/filters_sort.gif" width="250" alt="Filters & sort"/>
  </div>

  <div style="display: flex; flex-direction: column; align-items: center;">
    <span style="margin-bottom: 8px; font-weight: bold;">Favorites</span>
    <img src="/assets/gif/favorites.gif" width="250" alt="Favorites"/>
  </div>
</div>

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