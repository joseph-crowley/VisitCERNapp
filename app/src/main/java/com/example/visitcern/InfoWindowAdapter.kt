package com.example.visitcern


import android.content.Context
import android.graphics.drawable.Drawable
import android.media.Image
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
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

        val title: String? = marker.title
        val snippet: String? = marker.snippet
//        val titleUi = v.findViewById<TextView>(R.id.title)

        val tvGir = v.findViewById<View>(R.id.tvgir) as TextView
        val tvDetails = v.findViewById<View>(R.id.tvd) as TextView
        var imageView = v.findViewById<View>(R.id.image) as ImageView
        var img = 0

        when (title) {
            "Globe"-> img = R.drawable.globe
            "Restaurant 1" -> img = R.drawable.r1
            "CERN Reception" -> img = R.drawable.reception
            "Wandering the Immeasurable" -> img = R.drawable.immeasurable
            "ATLAS Experiment" -> img = R.drawable.atlas
            "Main Auditorium"-> img = R.drawable.auditorium
        }
        
        imageView.setImageResource(img)
        tvGir.text = title
        tvDetails.text = snippet

        return v
    }


}