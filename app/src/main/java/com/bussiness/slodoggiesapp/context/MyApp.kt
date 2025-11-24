package com.bussiness.slodoggiesapp.context

import android.app.Application
import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.android.libraries.places.api.Places
import com.google.firebase.FirebaseApp
import com.google.firebase.installations.FirebaseInstallations
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApp : Application() {

    override fun onCreate() {
        super.onCreate()
        if (!Places.isInitialized()) {
            Places.initialize(this, "AIzaSyDyJ8qKFZj-GibbXlON9L8ErJzZm4ZlBKs")
        }

        FirebaseApp.initializeApp(this)
        FirebaseMessaging.getInstance().isAutoInitEnabled = true
        FirebaseInstallations.getInstance().id
            .addOnCompleteListener { task: Task<String> ->
                if (!task.isSuccessful) {
                    Log.w("FIS", "getId failed", task.exception)
                    return@addOnCompleteListener
                }
                Log.d("FIS", "Installation ID: " + task.result)
            }

    }
}
