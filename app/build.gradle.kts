plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.hiltAndroid)
    alias(libs.plugins.jetbrains.kotlin.serialization)
    kotlin("kapt")
}

android {
    namespace = "ru.moonlight.mobile"
    compileSdk = libs.versions.targetSdk.get().toInt()

    defaultConfig {
        applicationId = "ru.moonlight.mobile"
        minSdk = libs.versions.minSdk.get().toInt()
        targetSdk = libs.versions.targetSdk.get().toInt()
        versionCode = libs.versions.versionCode.get().toInt()
        versionName = libs.versions.versionName.get()

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.kotlinCompilerExtensionVersion.get()
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

}

dependencies {
    implementation(project(":core:ui"))
    implementation(project(":core:network"))
    implementation(project(":core:data"))
    implementation(project(":core:database"))
    implementation(project(":core:common"))
    implementation(project(":feature-auth-signin"))
    implementation(project(":feature-auth-signup-registration"))
    implementation(project(":feature-auth-signup-confirmcode"))
    implementation(project(":feature-auth-signup-complete"))
    implementation(project(":feature-catalog"))
    implementation(project(":feature-catalog-categories"))
    implementation(project(":feature-catalog-product"))
    implementation(project(":feature-cart"))
    implementation(project(":feature-profile"))
    implementation(project(":feature-profile-edit"))
    implementation(project(":feature-profile-changepassword"))
    implementation(project(":feature-order"))
    implementation(project(":feature-favourites"))


    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)

    implementation(libs.androidx.material3.adaptive.navigation.suite.android)

    implementation(libs.androidx.navigation.compose)
    implementation(libs.accompanist.systemuicontroller)

    implementation(libs.kotlinx.serialization.json)

//    //room
//    implementation(libs.androidx.room.ktx)
//    //kapt(libs.androidx.room.compiler)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3.android)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}

kapt {
    correctErrorTypes = true
}