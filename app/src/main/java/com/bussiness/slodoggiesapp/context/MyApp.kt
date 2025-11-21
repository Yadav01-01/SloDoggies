package com.bussiness.slodoggiesapp.context

import android.app.Application
import com.google.android.libraries.places.api.Places
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApp : Application() {

    override fun onCreate() {
        super.onCreate()
        if (!Places.isInitialized()) {
            Places.initialize(this, "AIzaSyDyJ8qKFZj-GibbXlON9L8ErJzZm4ZlBKs")
        }
    }
}
