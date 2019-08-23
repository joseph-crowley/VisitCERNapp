package com.example.visitcern

import android.content.Context
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.os.Handler
import android.os.SystemClock
import android.util.Log
import android.view.animation.LinearInterpolator
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.*

class VisitCernLocationManager {
    private var context: Context? = null
    private var isGpsEnabled = false
    private var isNetworkEnabled = false
    private var location: Location? = null
    private var locationManager: LocationManager? = null
    private lateinit var mapHandler: VisitCernMapHandler
    private var following = true;

    constructor(context: Context, map: GoogleMap?) {
        this.context = context
        mapHandler = VisitCernMapHandler(map)
        locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager

    }

    private val locationListener: LocationListener = object : LocationListener {
        override fun onLocationChanged(location: Location?) {
            Log.d("LOCATION LISTENER", "LocationInfo changed")
            if (location != null) {
                mapHandler.locationChanged(location, following)
            }

            if (location == null) {
                Log.d("LOCATION LISTENER", "location is null")
            }
        }

        override fun onProviderDisabled(p0: String?) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun onProviderEnabled(p0: String?) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun onStatusChanged(p0: String?, p1: Int, p2: Bundle?) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }
    }

    fun getCurrentLocation(): Location? {
        val locMan = locationManager
        var loc: Location? = null
        if (locMan != null) {
            isNetworkEnabled = locMan.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
            isGpsEnabled = locMan.isProviderEnabled(LocationManager.GPS_PROVIDER);

            if (isGpsEnabled || isNetworkEnabled) {
                if (isNetworkEnabled) {
                    try {
                        loc = locMan.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
                    } catch (ex: SecurityException) {
                        Log.d("VISIT CERN LOC MAN", "Insufficient permissions for getting network location")
                    }
                }
                if (isGpsEnabled) {
                    try {
                        loc = locMan.getLastKnownLocation(LocationManager.GPS_PROVIDER)
                    } catch (ex: SecurityException) {
                        Log.d("VISIT CERN LOC MAN", "Insufficient permissions for getting gps location")
                        ex.printStackTrace()
                    }
                }
            }
        }

        return loc
    }

    fun requestLocationUpdates() {
        val locMan = locationManager
        if (locMan != null) {
            isNetworkEnabled = locMan.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
            isGpsEnabled = locMan.isProviderEnabled(LocationManager.GPS_PROVIDER);

            if (isGpsEnabled || isNetworkEnabled) {
                if (isNetworkEnabled) {
                    try {
                        locMan.requestLocationUpdates(
                            LocationManager.NETWORK_PROVIDER,
                            10,
                            1f,
                            locationListener
                        )
                    } catch (ex: SecurityException) {
                        Log.d("VISIT CERN LOC MAN", "Insufficient permissions for requesting network location updates")
                        ex.printStackTrace()
                    }
                }
                if (isGpsEnabled) {
                    try {
                        locMan.requestLocationUpdates(
                            LocationManager.GPS_PROVIDER,
                            10,
                            1f,
                            locationListener
                        )
                    } catch (ex: SecurityException) {
                        Log.d("VISIT CERN LOC MAN", "Insufficient permissions for requesting gps location updates")
                        ex.printStackTrace()
                    }
                }
            }
        }
    }

    fun moveCamera(location: Location) {
        val latLng = LatLng(location.latitude, location.longitude)
        mapHandler.follow(latLng)
    }

    fun setFollow(follow: Boolean) {
        this.following = follow
    }
}

