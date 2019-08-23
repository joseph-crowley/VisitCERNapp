package com.example.visitcern

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat

class QuizActivity : AppCompatActivity() {

    var correctAnswer = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)

        val question = intent.getStringExtra(EXTRA_LOCATION_QUESTION)
        val answer1 = intent.getStringExtra(EXTRA_LOCATION_ANSWER1)
        val answer2  = intent.getStringExtra(EXTRA_LOCATION_ANSWER2)
        val answer3 = intent.getStringExtra(EXTRA_LOCATION_ANSWER3)
        val answer4 = intent.getStringExtra(EXTRA_LOCATION_ANSWER4)
        correctAnswer = intent.getStringExtra(EXTRA_LOCATION_CORRECT_ANSWER)

        val textView = findViewById<TextView>(R.id.questionTextView).apply {
            text = question
        }
        val answer_1_button = findViewById<TextView>(R.id.answer1Button).apply {
            text = answer1
        }
        val answer_2_button = findViewById<TextView>(R.id.answer2Button).apply {
            text = answer2
        }
        val answer_3_button = findViewById<TextView>(R.id.answer3Button).apply {
            text = answer3
        }
        val answer_4_button = findViewById<TextView>(R.id.answer4Button).apply {
            text = answer4
        }
    }

    fun onClick(view: View) {
        val id = view.id
        val button = findViewById<Button>(id)
        val buttonText = button.text.toString()

        if (buttonText == correctAnswer) {
            val green = ContextCompat.getColor(this, R.color.correctGreen)
            button.setBackgroundColor(green)
        } else {
            val red = ContextCompat.getColor(this, R.color.wrongRed)
            button.setBackgroundColor(red)
        }


    }
}
