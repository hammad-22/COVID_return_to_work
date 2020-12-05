package com.example.covidreturntowork

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class CheckinFragment : AppCompatActivity() {

    private lateinit var buttonSubmit: Button
    private lateinit var databaseResults: DatabaseReference
    private val mResult = arrayListOf<String>("no symptoms", "quarantine", "critical")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_checkin)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigationView.setOnNavigationItemSelectedListener{
            when(it.itemId) {
                R.id.navigation_home -> {
                    val intent = Intent(this, HomeFragment::class.java)
                    startActivity(intent)
                }
                R.id.navigation_whatToDo -> {
                    val intent = Intent(this, WTDFragment::class.java)
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

            true
        }

        buttonSubmit = findViewById(R.id.submitButton)
    }

    // Add result to database
    private fun addResult() {
        //TODO change result to real function that call result
        val result = mResult[0]
        val id = databaseResults.push().key
        Log.i("ID", id!!)
        if (id != null) {
            databaseResults.child(id).setValue(result)
            Toast.makeText(this, "Result Added", Toast.LENGTH_LONG)

        }
    }
}