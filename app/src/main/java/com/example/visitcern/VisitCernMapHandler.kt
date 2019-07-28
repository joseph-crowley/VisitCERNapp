package com.example.visitcern

import android.graphics.Color
import android.location.Location
import android.os.Handler
import android.os.SystemClock
import android.util.Log
import android.view.animation.LinearInterpolator
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.*

class VisitCernMapHandler {
    private var map: GoogleMap? = null
    private var marker: Marker? = null
    private var markerOptions = MarkerOptions().position(LatLng(0.0, 0.0)).icon(BitmapDescriptorFactory.fromResource(R.drawable.pin))

    constructor(map: GoogleMap?) {
        this.map = map
        addMarker(markerOptions)
    }

    fun locationChanged(newLocation: Location, follow: Boolean) {
        Log.d("MAP HANDLER", "Location changed")
        val newLatLng = LatLng(newLocation.latitude, newLocation.longitude)
        animateMarker(newLatLng)
        if (follow) follow(newLatLng)
    }

    fun follow(newLatLng: LatLng) {
        if (map != null) {
            animateMarker(newLatLng)
            map!!.moveCamera(CameraUpdateFactory.newLatLng(newLatLng))
            map!!.animateCamera(CameraUpdateFactory.zoomTo(17f))
        }
    }

    fun animateMarker(newLatLng: LatLng, showMarker: Boolean = true) {
        val map = this.map
        var marker = this.marker

        Log.d("MAP HANDLER", "Animating marker")

        if (marker == null) {
            addMarker(markerOptions.position(newLatLng))
        }

        if (map != null && marker != null) {
            val handler = Handler()
            val startTime = SystemClock.uptimeMillis()
            val proj = map.getProjection()
            val startPoint = proj.toScreenLocation(marker.position)
            val startLatLng = proj.fromScreenLocation(startPoint)

            val duration: Long = 500

            val interpolator = LinearInterpolator()

            handler.post(object : Runnable {
                override fun run() {
                    val elapsedTime = SystemClock.uptimeMillis() - startTime
                    val t = Math.min(1.0f, interpolator.getInterpolation(elapsedTime.toFloat() / duration))

                    val lng = t * newLatLng.longitude + (1 - t) * startLatLng.longitude
                    val lat = t * newLatLng.latitude + (1 - t) * startLatLng.latitude


                    marker.position = LatLng(lat, lng)
                    Log.d("MAP HANDLER", "Moved marker to " + marker.position)

                    if (t < 1.0) {
                        // Post again 16ms later.
                        handler.postDelayed(this, 16)
                    } else {
                        marker.isVisible = true
                    }
                }
            })
        } else {
            Log.d("MAP HANDLER", "map or marker was null in animateMarker()")
        }
    }

    private fun addMarker(markerOptions: MarkerOptions) {
        Log.d("MAP HANDLER", "Adding marker at " + markerOptions.position)
        if (this.marker == null && this.map != null) {
            marker = map!!.addMarker(markerOptions)
        }
    }
}