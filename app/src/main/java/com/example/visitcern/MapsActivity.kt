package com.example.visitcern

import androidx.fragment.app.FragmentActivity

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import android.util.Log
import androidx.core.app.ActivityCompat
import com.google.android.gms.maps.CameraUpdateFactory

import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.floatingactionbutton.FloatingActionButton

const val EXTRA_LOCATION_QUESTION = "com.example.visitcern.LOCATION_QUESTION"
const val EXTRA_LOCATION_ANSWER1 = "com.example.visitcern.LOCATION_ANSWER1"
const val EXTRA_LOCATION_ANSWER2 = "com.example.visitcern.LOCATION_ANSWER2"
const val EXTRA_LOCATION_ANSWER3 = "com.example.visitcern.LOCATION_ANSWER3"
const val EXTRA_LOCATION_ANSWER4 = "com.example.visitcern.LOCATION_ANSWER4"
const val EXTRA_LOCATION_CORRECT_ANSWER = "com.example.visitcern.LOCATION_CORRECT_ANSWER"

class MapsActivity : FragmentActivity(), OnMapReadyCallback, GoogleMap.OnInfoWindowClickListener {

    private var mMap: GoogleMap? = null
    private var btn: Button? = null
    private var marker: Marker? = null
    private lateinit var locMan: VisitCernLocationManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        btn = findViewById(R.id.btn)
        btn?.let {
            it.setOnClickListener {
                finish()
            }
        }


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
        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1)
        locMan = VisitCernLocationManager(this, mMap)

        // Setting a custom info window adapter for the google map
        val markerInfoWindowAdapter = InfoWindowAdapter(applicationContext)
        googleMap.setInfoWindowAdapter(markerInfoWindowAdapter)
        googleMap.setOnInfoWindowClickListener(this)
        googleMap.setOnMapClickListener( { unfollow() } )

        addMarkers()

        // Adding and showing marker when the map is touched

//        mMap!!.clear()
//        val markerOptions = MarkerOptions()
//        markerOptions.position(globe)
//        marker = mMap!!.addMarker(markerOptions)

        locMan.requestLocationUpdates()
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(46.233970, 6.055727), 16.0f))
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        Log.d("PERMISSION RESULT", "Permission result received")
        when (requestCode) {
            1 -> {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.d("PERMISSION RESULT", "LocationInfo permission granted")
                } else {
                }
                return
            }
        }
    }

    override fun onInfoWindowClick(marker: Marker) {
//        Toast.makeText(this, "clicked", Toast.LENGTH_SHORT).show()

        val tag = marker.tag
        if (tag is LocationInfo) {
            val intent = Intent(this, QuizActivity::class.java).apply {
                putExtra(EXTRA_LOCATION_QUESTION, tag.question)
                putExtra(EXTRA_LOCATION_ANSWER1, tag.answer1)
                putExtra(EXTRA_LOCATION_ANSWER2, tag.answer2)
                putExtra(EXTRA_LOCATION_ANSWER3, tag.answer3)
                putExtra(EXTRA_LOCATION_ANSWER4, tag.answer4)
                putExtra(EXTRA_LOCATION_CORRECT_ANSWER, tag.correct_answer)

            }
            startActivity(intent)
        }




    }

    private fun addMarkers() {
        val globe = LatLng(46.233970, 6.055727)
        val r1 = LatLng(46.230986, 6.054607)
        val reception = LatLng(46.233036, 6.055734)
        val wanderingTheImmeasurable = LatLng(46.233682, 6.056147)
        val atlas = LatLng(46.235829, 6.055338)
        val auditorium = LatLng(46.231429, 6.054261)

        mMap?.let {
            val globe_description = getResources().getString(R.string.globe_description)
            val r1_description = getResources().getString(R.string.r1_description)
            val reception_description = getResources().getString(R.string.reception_description)
            val immeasurable_description = getResources().getString(R.string.immeasurable_description)
            val ATLAS_description = getResources().getString(R.string.ATLAS_description)
            val auditorium_description = getResources().getString(R.string.auditorium_description)

            var globe = mMap!!.addMarker(MarkerOptions().position(globe).title("Globe").snippet(globe_description))
            globe.tag = LocationInfo("?", "!", ".", ",", ";", ";")
            var r1 = mMap!!.addMarker(MarkerOptions().position(r1).title("Restaurant 1").snippet(r1_description))
            r1.tag = LocationInfo("?", "!", ".", ",", ";", ";")
            var reception = mMap!!.addMarker(MarkerOptions().position(reception).title("CERN Reception").snippet(reception_description))
            reception.tag = LocationInfo("?", "!", ".", ",", ";", ";")
            var immeasurable = mMap!!.addMarker(MarkerOptions().position(wanderingTheImmeasurable).title("Wandering the Immeasurable").snippet(immeasurable_description))
            immeasurable.tag = LocationInfo("?", "!", ".", ",", ";", ";")
            var atlas = mMap!!.addMarker(MarkerOptions().position(atlas).title("ATLAS Experiment").snippet(ATLAS_description))
            atlas.tag = LocationInfo("?", "!", ".", ",", ";", ";")
            var auditorium = mMap!!.addMarker(MarkerOptions().position(auditorium).title("Main Auditorium").snippet(auditorium_description))
            auditorium.tag = LocationInfo("?", "!", ".", ",", ";", ";")
        }
    }

    fun locate() {
        Log.d("MAPS ACTIVITY", "Locate pressed")
        val currentLocation = locMan.getCurrentLocation()
        if (currentLocation != null) {
            Log.d("MAPS ACTIVITY", "Locating")
            locMan.moveCamera(currentLocation)
            locMan.setFollow(true)
        }
    }

    fun unfollow() {
        Log.d("MAPS ACTIVITY", "Unfollowing")
        locMan.setFollow(false)
    }
}
