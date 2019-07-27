package com.example.visitcern

import androidx.fragment.app.FragmentActivity

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions

class MapsActivity : FragmentActivity(), OnMapReadyCallback, GoogleMap.OnInfoWindowClickListener {

    private var mMap: GoogleMap? = null
    private var btn: Button? = null
    private var marker: Marker? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        btn = findViewById(R.id.btn)
        btn!!.setOnClickListener { marker!!.hideInfoWindow() }

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

        // Add a marker in Sydney and move the camera
        val globe = LatLng(46.233970, 6.055727)
        mMap!!.addMarker(MarkerOptions().position(globe))
        mMap!!.moveCamera(CameraUpdateFactory.newLatLngZoom(globe, 16.0f))

        // Setting a custom info window adapter for the google map
        val markerInfoWindowAdapter = InfoWindowAdapter(applicationContext)
        googleMap.setInfoWindowAdapter(markerInfoWindowAdapter)

        // Adding and showing marker when the map is touched

        mMap!!.clear()
        val markerOptions = MarkerOptions()
        markerOptions.position(globe)
        mMap!!.animateCamera(CameraUpdateFactory.newLatLng(globe))
        marker = mMap!!.addMarker(markerOptions)
        // marker.showInfoWindow();

        googleMap.setOnInfoWindowClickListener(this)

    }

    override fun onInfoWindowClick(marker: Marker) {
        Toast.makeText(this, "clicked", Toast.LENGTH_SHORT).show()
    }
}