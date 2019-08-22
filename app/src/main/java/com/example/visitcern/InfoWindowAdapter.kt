package com.example.visitcern


import android.content.Context
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
        var image = v.findViewById<View>(R.id.image) as ImageView
        //val uri = Uri.parse("drawable/globe.jpg")
        //image.setImageURI(uri)
        //Log.d(uri.toString(), "")
        // TextView tvLng = (TextView) v.findViewById(R.id.tv_lng);
        tvGir.text = title
        tvDetails.text = snippet
        //tvLng.setText("Longitude:"+ latLng.longitude);
        return v
    }


}