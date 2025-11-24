// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false
    alias(libs.plugins.hilt) apply false
    id("com.google.gms.google-services") version "4.4.2" apply false
}

buildscript {
    dependencies {
        classpath ("com.google.gms:google-services:4.4.2")
    }
}

subprojects {
    configurations.all {
        resolutionStrategy.eachDependency {
            if (requested.group == "androidx.core" && requested.name == "core-ktx") {
                useVersion("1.15.0")
                because("AGP 8.5.1 does not support core-ktx 1.16.0")
            }
            if (requested.group == "androidx.core" && requested.name == "core") {
                useVersion("1.15.0")
                because("AGP 8.5.1 does not support core 1.16.0")
            }
        }
    }
}