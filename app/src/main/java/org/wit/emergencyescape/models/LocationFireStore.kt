package org.wit.emergencyescape.models

import android.content.Context
import android.graphics.Bitmap
import android.location.Location
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import org.jetbrains.anko.AnkoLogger
import java.io.ByteArrayOutputStream
import java.io.File

class LocationFireStore(val context: Context) : AnkoLogger {
    val locations = ArrayList<LocationModel>()
    lateinit var userId : String
    lateinit var db: DatabaseReference
    lateinit var st: StorageReference

    fun findAll(id: Long):LocationModel? {
        val foundLocation: LocationModel?= locations.find{p ->p.id ==id}
        return foundLocation
    }

    fun create(location: LocationModel){
        val key = db.child("users").child(userId).child("locations").push().key
        location.fbId = key!!
        locations.add(location)
        db.child("users").child(userId).child("locations").child(key).setValue(location)
    }

    fun update(location :LocationModel){
        val foundLocation: LocationModel?= locations.find{p ->p.fbId == location.fbId}
        if( foundLocation != null){
            foundLocation.lat = location.lat
            foundLocation.lng = location.lng
            foundLocation.zoom = location.zoom

            db.child("users").child(userId).child("hillforts").child(location.fbId).setValue(location)
        }
        db.child("users").child(userId).child("hillforts").child(location.fbId).setValue(location)
    }

    fun delete(location: LocationModel) {
        db.child("users").child(userId).child("hillforts").child(location.fbId).removeValue()
        locations.remove(location)
    }

    fun clear(){
        locations.clear()
    }

    fun fetchLocation( locationsReady: ()-> Unit) {
        val valueEventListener = object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                dataSnapshot.children.mapNotNullTo(locations) { it.getValue<LocationModel>(LocationModel::class.java) }
                locationsReady()
            }
        }
        userId = FirebaseAuth.getInstance().currentUser!!.uid
        db = FirebaseDatabase.getInstance().reference
        st = FirebaseStorage.getInstance().reference
        locations.clear()
        db.child("users").child(userId).child("locations").addListenerForSingleValueEvent(valueEventListener)
    }
}