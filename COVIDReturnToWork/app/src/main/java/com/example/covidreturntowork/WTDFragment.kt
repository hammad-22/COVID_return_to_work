package com.example.covidreturntowork

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class WTDFragment : AppCompatActivity() {

    private lateinit var mResult: String
    private lateinit var instructions: TextView
    private var savedResult = "No Information Inputted"

    private val sharedPrefFile = "Result"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_wtd)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigationView.selectedItemId = R.id.navigation_whatToDo
        bottomNavigationView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.navigation_home -> {
                    val intent = Intent(this, HomeFragment::class.java)
                    startActivity(intent)
                }
                R.id.navigation_history -> {
                    val intent = Intent(this, HistoryFragment::class.java)
                    startActivity(intent)
                }
                R.id.navigation_logout -> {
                    val intent = Intent(this, LogoutFragment::class.java)
                    startActivity(intent)
                }
                R.id.navigation_checkIn -> {
                    val intent = Intent(this, CheckinFragment::class.java)
                    startActivity(intent)
                }
            }
            overridePendingTransition(0,0)
            true
        }

        val sharedPreferences: SharedPreferences = this.getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE)

        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT


        instructions = findViewById(R.id.instructions)
        if(intent.getStringExtra("Result") != null) {

            mResult = intent.getStringExtra("Result").toString()
            instructions.text = mResult
            val editor:SharedPreferences.Editor = sharedPreferences.edit()

            editor.putString("Result", instructions.text.toString())
            editor.apply()
            editor.commit()
        } else {
            if(!(sharedPreferences.getString("Result", "").equals(""))){
                val sharedNameValue = sharedPreferences.getString("Result","")
                instructions.text = sharedNameValue
            } else {
                instructions.text = savedResult
            }
        }


    }

    override fun onResume() {
        super.onResume()

    }

    override fun onSaveInstanceState(outState: Bundle){
        outState.putString("Result", instructions.text.toString())
        super.onSaveInstanceState(outState)
    }


    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        savedResult = savedInstanceState.getString("Result").toString()
        instructions.text = savedResult

       requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

    }
}