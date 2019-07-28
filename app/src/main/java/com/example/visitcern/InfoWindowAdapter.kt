package com.example.visitcern


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView

import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker

class InfoWindowAdapter(context: Context) : GoogleMap.InfoWindowAdapter {

    private val context: Context

    init {
        this.context = context.applicationContext
    }

    override fun getInfoWindow(marker: Marker): View? {
        return null
    }

    override fun getInfoContents(marker: Marker): View {

        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val v = inflater.inflate(R.layout.layout, null)

        val tvGir = v.findViewById<View>(R.id.tvgir) as TextView
        val tvDetails = v.findViewById<View>(R.id.tvd) as TextView
        // TextView tvLng = (TextView) v.findViewById(R.id.tv_lng);
        tvGir.text = "This is the Globe"
        tvDetails.text = "27 metres high and 40 metres in diameter, it's about the size of the dome of Saint Peter's in Rome! A unique visual landmark by day and by night, the Globe of Science and Innovation is a symbol of Planet Earth. It is CERN's outreach tool for its work in the fields of science, particle physics, leading-edge technologies and their applications in everyday life.\n" +
                "On the ground floor of the Globe, the \"Universe of Particles\" exhibition takes the visitor on a journey deep into the world of particles and back to the Big Bang.\n" +
                "The free exhibition is for visitors to confront the great questions of contemporary physics, currently being explored by the CERN via the LHC and other accelerators.\n" +
                "Dive into the fascinating world of particles.\n"
        //tvLng.setText("Longitude:"+ latLng.longitude);
        return v
    }


}