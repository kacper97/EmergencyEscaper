package org.wit.emergencyescape.main

import android.app.Application
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.wit.emergencyescape.models.*

class MainApp : Application(), AnkoLogger {

    lateinit var locations : ArrayList<LocationModel>
    lateinit var firestore : LocationFireStore
    override fun onCreate() {
        super.onCreate()
        firestore = LocationFireStore(applicationContext)
        locations = firestore.locations

        info("Hillfort started")

        //
    }
}