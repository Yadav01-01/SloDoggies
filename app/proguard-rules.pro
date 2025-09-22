####################################
## General Android & Kotlin rules ##
####################################

# Keep your app classes
-keep class com.bussiness.slodoggiesapp.** { *; }

# Keep Activities, Fragments, ViewModels
-keep class * extends android.app.Activity
-keep class * extends androidx.fragment.app.Fragment
-keep class * extends androidx.lifecycle.ViewModel

# Keep Parcelable & Serializable
-keepclassmembers class * implements android.os.Parcelable {
    public static final android.os.Parcelable$Creator CREATOR;
}
-keepclassmembers class * implements java.io.Serializable { *; }

# Keep annotations (needed for Hilt, Retrofit, Gson)
-keepattributes *Annotation*
-keepattributes Signature

# Keep Kotlin metadata
-keep class kotlin.Metadata { *; }


##########################
## Hilt / Dagger rules ##
##########################

-keep class dagger.hilt.** { *; }
-keep class androidx.hilt.** { *; }
-dontwarn dagger.hilt.**
-dontwarn javax.annotation.**

# Hilt generated classes
-keep class * extends dagger.hilt.android.internal.managers.ViewComponentManager$FragmentContextWrapper { *; }


##############################
## Retrofit / Gson / OkHttp ##
##############################

# Retrofit interfaces
-keep interface com.bussiness.slodoggiesapp.data.api.** { *; }

# Retrofit models (needed for Gson reflection)
-keep class com.bussiness.slodoggiesapp.data.model.** { *; }

# Retrofit & OkHttp warnings
-dontwarn okhttp3.**
-dontwarn okio.**
-dontwarn retrofit2.**

# Gson
-keep class com.google.gson.** { *; }


#####################
## Jetpack Compose ##
#####################

-keep class androidx.compose.** { *; }

# Keep Composable methods
-keepclassmembers class * {
    @androidx.compose.runtime.Composable <methods>;
}

# Avoid Compose reflection stripping
-keepclassmembers class * {
    @androidx.compose.runtime.Composable *;
}

# Already present
-dontwarn androidx.compose.ui.text.font.**


###################
## Media3 Player ##
###################

-dontwarn com.google.android.exoplayer2.**
-keep class com.google.android.exoplayer2.** { *; }


###############
## Coil (v2) ##
###############

-dontwarn coil.**
-keep class coil.** { *; }


#######################
## Accompanist libs  ##
#######################

-dontwarn com.google.accompanist.**


#####################
## Material Design ##
#####################

-dontwarn com.google.android.material.**
-keep class com.google.android.material.** { *; }


######################
## Logging Cleanup  ##
######################

# Remove Log calls in release for smaller size
-assumenosideeffects class android.util.Log {
    public static *** v(...);
    public static *** d(...);
    public static *** i(...);
    public static *** w(...);
    public static *** e(...);
}
