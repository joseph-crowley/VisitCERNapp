package com.example.visitcern

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
//import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity;

import kotlinx.android.synthetic.main.activity_start.*

class StartActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)
    }

    fun quizstart(view: View) {
        // Create an Intent to start the second activity
        val mapact = Intent(this, MapsActivity::class.java)

        // Start the new activity.
        startActivity(mapact)

    }
}


