package org.wit.emergencyescape.views.location

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.fragment.app.FragmentActivity

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import org.wit.emergencyescape.R
import java.io.IOException


class MapsActivity : FragmentActivity(), OnMapReadyCallback {

    private var mMap: GoogleMap? = null
    internal var marker: Marker? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment!!.getMapAsync(this)
        val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_LOCATION_PERMISSION
            )
        }
        val locationListener = object : LocationListener {
            override fun onLocationChanged(location: Location) {
                val latitude = location.latitude
                val longitude = location.longitude
                //get the location name from latitude and longitude
                val geocoder = Geocoder(applicationContext)
                try {
                    val addresses = geocoder.getFromLocation(latitude, longitude, 1)
                    var result = addresses[0].locality + ":"
                    result += addresses[0].countryName
                    val latLng = LatLng(latitude, longitude)
                    if (marker != null) {
                        marker!!.remove()
                        marker = mMap!!.addMarker(MarkerOptions().position(latLng).title(result))
                        mMap!!.setMaxZoomPreference(20f)
                        mMap!!.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12.0f))
                    } else {
                        marker = mMap!!.addMarker(MarkerOptions().position(latLng).title(result))
                        mMap!!.setMaxZoomPreference(20f)
                        mMap!!.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 21.0f))
                    }


                } catch (e: IOException) {
                    e.printStackTrace()
                }

            }

            override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {

            }

            override fun onProviderEnabled(provider: String) {

            }

            override fun onProviderDisabled(provider: String) {

            }
        }
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0f, locationListener)
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0f, locationListener)
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera
        //        LatLng sydney = new LatLng(-34, 151);
        //        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        //        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

    override fun onStop() {
        super.onStop()
    }

    companion object {
        private val REQUEST_LOCATION_PERMISSION = 1
    }
}
