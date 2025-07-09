import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.JvmTarget.Companion.fromTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinJvmCompile

plugins {
    alias(libs.plugins.application.android)
    alias(libs.plugins.org.jetbrains.kotlin.android)
    id("kotlin-parcelize")
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.ksp.plugin)
    alias(libs.plugins.serialization.ktx)
    alias(libs.plugins.spotless)
}

android {
    namespace = "io.github.livenlearnaday.weatherapp"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "io.github.livenlearnaday.weatherapp"
        minSdk = libs.versions.android.minSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
        vectorDrawables.useSupportLibrary = true

        multiDexEnabled = true

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        /* Retrieves API from local.properties */
        val properties = org.jetbrains.kotlin.konan.properties.Properties()
        properties.load(project.rootProject.file("local.properties").inputStream())

        buildConfigField("String", "WEATHERSTACK_KEY", "${properties["WEATHERSTACK_KEY"]}")
        buildConfigField("String", "WEATHERSTACK_ENDPOINT", "\"https://api.weatherstack.com\"")

        buildConfigField("String", "OPENWEATHER_KEY", "${properties["OPENWEATHER_KEY"]}")
        buildConfigField("String", "OPENWEATHER_ENDPOINT", "\"https://api.openweathermap.org\"")
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
        sourceCompatibility = JavaVersion.toVersion(libs.versions.jvm.get())
        targetCompatibility = JavaVersion.toVersion(libs.versions.jvm.get())
    }

    kotlin {
        compilerOptions {
            jvmTarget = fromTarget(libs.versions.jvm.get())
            allWarningsAsErrors = false
            freeCompilerArgs = listOf(
                "-opt-in=androidx.compose.material3.ExperimentalMaterial3Api",
                "-opt-in=kotlinx.coroutines.ExperimentalCoroutinesApi"
            )
        }
    }

    buildFeatures {
        buildConfig = true
        compose = true
    }
}

dependencies {
    // androidx
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.constraintlayout.compose)
    implementation(libs.androidx.lifecycle.runtime.compose)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.bundles.compose)

    // navigation
    implementation(libs.bundles.navigation)

    //utils
    implementation(libs.timber)
    implementation(libs.jetbrains.kotlin.datetime)

    // coroutines
    implementation(libs.coroutines)

    // koin di
    implementation(libs.bundles.koin)
    implementation(libs.bundles.koin.compose)

    // image loading
    implementation(libs.bundles.coil)

    // network
    implementation(libs.bundles.ktor)

    // test libs
    testImplementation(libs.bundles.test.impl)
    androidTestImplementation(libs.bundles.android.test.impl)
    androidTestImplementation(libs.bundles.navigation)
    debugImplementation(libs.bundles.debug.impl)
    androidTestImplementation(libs.ktorClientCio)
}
