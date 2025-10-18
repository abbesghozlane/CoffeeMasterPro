plugins {
    id("com.android.application")
    kotlin("android")
    id("kotlin-kapt")
}

android {
    namespace = "com.coffeemaster.app"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.coffeemaster.app"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "0.1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.4"
    }

    packagingOptions {
        resources.excludes += "META-INF/AL2.0"
        resources.excludes += "META-INF/LGPL2.1"
    }
}

dependencies {
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.3")
    implementation("androidx.activity:activity-compose:1.8.0")

    // Compose
    implementation("androidx.compose.ui:ui:1.5.3")
    implementation("androidx.compose.material:material:1.5.3")
    implementation("androidx.compose.ui:ui-tooling-preview:1.5.3")
    debugImplementation("androidx.compose.ui:ui-tooling:1.5.3")

    // Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")
    // Room
    implementation("androidx.room:room-runtime:2.6.1")
    kapt("androidx.room:room-compiler:2.6.1")
    implementation("androidx.room:room-ktx:2.6.1")

    // Navigation Compose
    implementation("androidx.navigation:navigation-compose:2.7.0")

    // PDF / DOCX (stubs) - using simple well-known libraries
    implementation("com.itextpdf:itext7-core:7.2.5") // may require configuring license for advanced features
    implementation("org.apache.poi:poi-ooxml:5.2.3")

    // Optional image loading, serialization
    implementation("com.squareup.moshi:moshi:1.15.0")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}
