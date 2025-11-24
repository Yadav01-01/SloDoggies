plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.hilt)
    id("org.jetbrains.kotlin.kapt")
    id ("com.google.gms.google-services")
}

android {
    namespace = "com.bussiness.slodoggiesapp"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.bussiness.slodoggiesapp"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        buildConfig = true
    }
    kapt {
        correctErrorTypes = true
    }

    packaging {
        resources {
            excludes += "META-INF/versions/9/OSGI-INF/MANIFEST.MF"
        }
    }



}
dependencies {

    // Core
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)

    // Instrumentation tests (androidTest)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)

    // KAPT for tests
    kaptTest(libs.hilt.compiler)
    kaptAndroidTest(libs.hilt.compiler)

    // Debug tools
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // Compose BOM (manages versions for Compose libraries)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.compose.testing)

    // Navigation
    implementation(libs.androidx.navigation.compose)

    // Splash screen
    implementation(libs.androidx.core.splashscreen)

    // Accompanist
    implementation(libs.accompanist.pager)
    implementation(libs.accompanist.pager.indicators)
    implementation(libs.accompanist.flowlayout)

    // ExoPlayer
    implementation(libs.androidx.media3.exoplayer)
    implementation(libs.androidx.media3.ui)

    // Coil
    implementation(libs.coil.compose.v260)

    // Hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)

    // Hilt for Jetpack Compose navigation
    implementation(libs.androidx.hilt.navigation.compose)

    // Material
    implementation(libs.material)

    // Unit tests
    testImplementation(libs.junit)

    // Hilt compiler
    kapt(libs.hilt.compiler)
    kaptTest(libs.hilt.compiler)
    kaptAndroidTest(libs.hilt.compiler)

    //systemUI
    implementation (libs.accompanist.systemuicontroller)

    // Media3
    implementation(libs.androidx.media3.exoplayer.v180)
    implementation(libs.androidx.media3.ui.v180)
    implementation(libs.androidx.media3.common)
    // Retrofit + OkHttp
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.okhttp)
    implementation(libs.logging.interceptor)
    // Coroutines
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android)
    // Gson with lenient parsing
    implementation(libs.gson)
    //OkHttp
    implementation(platform(libs.okhttp.bom))
    implementation(libs.okhttp3.okhttp)
    implementation(libs.okhttp3.logging.interceptor)
    //lottie animation loader
    implementation(libs.lottie.compose)
    //google place API
//    implementation (libs.places)
    implementation(libs.places.v320)
    implementation (libs.accompanist.permissions)
    implementation (libs.play.services.location)
    implementation (libs.accompanist.swiperefresh)


    implementation (platform("com.google.firebase:firebase-bom:33.1.2"))
    implementation ("com.google.firebase:firebase-messaging")

}