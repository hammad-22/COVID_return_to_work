package com.example.covidreturntowork

import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class LogoutFragment : AppCompatActivity() {

    private lateinit var yesButton: Button
    private lateinit var noButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_logout)
        yesButton = findViewById(R.id.yesButton)
        noButton = findViewById(R.id.noButton)

        yesButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        noButton.setOnClickListener {
            val intent = Intent(this, HomeFragment::class.java)
            startActivity(intent)
        }
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

    }
}