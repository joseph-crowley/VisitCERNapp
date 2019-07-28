package com.example.visitcern

import androidx.fragment.app.FragmentActivity

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import android.util.Log
import android.view.View
import androidx.core.app.ActivityCompat

import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.activity_maps.*

class MapsActivity : FragmentActivity(), OnMapReadyCallback, GoogleMap.OnInfoWindowClickListener {

    private var mMap: GoogleMap? = null
    private var btn: Button? = null
    private var marker: Marker? = null
    private lateinit var locMan: VisitCernLocationManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        btn = findViewById(R.id.btn)
        btn!!.setOnClickListener { marker!!.hideInfoWindow() }

        val locateButton = findViewById<FloatingActionButton>(R.id.locateButton)
        locateButton.setOnClickListener( { locate() } )

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment!!.getMapAsync(this)
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

        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1);
        locMan = VisitCernLocationManager(this, mMap)

        // Setting a custom info window adapter for the google map
        val markerInfoWindowAdapter = InfoWindowAdapter(applicationContext)
        googleMap.setInfoWindowAdapter(markerInfoWindowAdapter)
        googleMap.setOnInfoWindowClickListener(this)

        addMarkers()

        // Adding and showing marker when the map is touched

//        mMap!!.clear()
//        val markerOptions = MarkerOptions()
//        markerOptions.position(globe)
//        marker = mMap!!.addMarker(markerOptions)

        locMan.requestLocationUpdates()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        Log.d("PERMISSION RESULT", "Permission result recieved")
        when (requestCode) {
            1 -> {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.d("PERMISSION RESULT", "Location permission granted")
                } else {
                }
                return
            }
        }
    }

    override fun onInfoWindowClick(marker: Marker) {
        Toast.makeText(this, "clicked", Toast.LENGTH_SHORT).show()
    }

    private fun addMarkers() {
        val globe = LatLng(46.233970, 6.055727)
        if (mMap != null) {
            mMap!!.addMarker(MarkerOptions().position(globe))
        }
    }

    fun locate() {
        Log.d("MAPS ACTIVITY", "Locate pressed")
        val currentLocation = locMan.getCurrentLocation()
        if (currentLocation != null) {
            Log.d("MAPS ACTIVITY", "Locating")
            locMan.moveCamera(currentLocation)
        }
    }
}
