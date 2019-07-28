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
        val r1 = LatLng(46.230986, 6.054607)
        val reception = LatLng(46.233036, 6.055734)
        val wanderingTheImmeasurable = LatLng(46.233682, 6.056147)
        val atlas = LatLng(46.235829, 6.055338)

        if (mMap != null) {
            mMap!!.addMarker(MarkerOptions().position(globe).title("Globe").snippet("27 metres high and 40 metres in diameter, it's about the size of the dome of Saint Peter's in Rome! A unique visual landmark by day and by night, the Globe of Science and Innovation is a symbol of Planet Earth. It is CERN's outreach tool for its work in the fields of science, particle physics, leading-edge technologies and their applications in everyday life.\n" +
                    "On the ground floor of the Globe, the \"Universe of Particles\" exhibition takes the visitor on a journey deep into the world of particles and back to the Big Bang.\n" +
                    "The free exhibition is for visitors to confront the great questions of contemporary physics, currently being explored by the CERN via the LHC and other accelerators.\n" +
                    "Dive into the fascinating world of particles.\n"))
            mMap!!.addMarker(MarkerOptions().position(r1).title("Restaurant 1").snippet("Drinks, food, or table tennis; Restaurant 1 has it all. Located in Building 501, Restaurant 1 serves as both a social hub and a place to eat. Open from Monday to Friday from 6h00 until 24h00, meals are served from 11h30 to 14h15 and from 18h to 21h30. It is also open on Saturdays and Sundays from 7h until 22h00 and meals are served from 11h30 to 14h00 and from 18h to 20h00. It proposes different menus from CHF 8.50 to CHF 16.00, including vegetarian options. If you’re not hungry, you will certainly find people enjoying a drink or playing table tennis, table football or cards. In the evenings, there is often music and other events taking place. If you want to relax from work, Restaurant 1 is the place to be."))
            mMap!!.addMarker(MarkerOptions().position(reception).title("CERN Reception").snippet("Welcome to CERN Meyrin campus. From this building you can find the reception, CERN shop and Microcosm exhibition. Our lovely reception is happy to help you with all kinds of questions and you can also get the visitor pass from here!\n \n Access and opening hours:\n" +
                    "\nReception\n" +
                    "Monday - Friday from 08:00 to 17:45\n" +
                    "Saturday from 08:30 to 17:15\n" +
                    "Phone number: +41 (0)22 767 76 76\n" +
                    "Internal number: 78484\n" +
                    "\nCERN shop\n" +
                    "Monday - Friday from 08:15 to 17:45\n" +
                    "Saturday from 09:00 to 17:15\n" +
                    "\nMicrocosm exhibition\n" +
                    "Monday - Friday from 08:30 to 17:30\n" +
                    "Saturday from 09:00 to 17:00\n"))
            mMap!!.addMarker(MarkerOptions().position(wanderingTheImmeasurable).title("Wandering the Immeasurable").snippet("Twisting 11 metres into the air, the sculpture, which is shaped like a giant ribbon, pays homage to great discoveries in physics through the ages. Some 37 metres of steel are laser cut on the outside surface with 396 discoveries in their language of origin. Each shows a scientist's name and the year and nature of his or her breakthrough in the field of physics, astrophysics or mathematics.\n" +
                    "The first inscription, written in cuneiform at the base of the sculpture, deals with sexagesimal calculations, discovered by the ancient Mesopotamians in around 1500 BC. Greek inscriptions follow from early thinkers such as Archimedes, before Chinese, Sanskrit and Arabic make their mark.\n" +
                    "Electroplated on the inside surface of the sculpture, these same discoveries are represented with scientific symbols – early Babylonian sky maps and algebraic equations including Pythagoras' theorem and Einstein's famous e=mc2, culminate with the complex Langrangian equation that describes the Standard Model of particle physics.\n" +
                    "\"The steel ribbon rises from the Earth and twists as if on a peregrination – the science does not know where it will lead,\" says CERN's Bernard Pellequer, who initiated the sculpture project.\n" +
                    "The sculpture was designed by Canadian artist Gayle Hermick and made possible by a one-off donation from the Fondation Meyrinoise du Casino. Swiss metalwork firm SENN-AG put the steel together.\n"))
            mMap!!.addMarker(MarkerOptions().position(atlas).title("ATLAS Experiment").snippet("ATLAS is one of the four major experiments at the Large Hadron Collider (LHC) at CERN. It is a general-purpose particle physics experiment run by an international collaboration and, together with CMS, is designed to exploit the full discovery potential and the huge range of physics opportunities that the LHC provides.\n" +
                    "\n" +
                    "ATLAS' scientific exploration uses precision measurement to push the frontiers of knowledge by seeking answers to fundamental questions such as: What are the basic building blocks of matter? What are the fundamental forces of nature? Could there be a greater underlying symmetry to our universe?\n" +
                    "\n" +
                    "ATLAS physicists test the predictions of the Standard Model, which encapsulates our current understanding of what the building blocks of matter are and how they interact. These studies can lead to ground-breaking discoveries, such as that of the Higgs boson, physics beyond the Standard Model and the development of new theories to better describe our universe.\n" +
                    "\n"))

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
