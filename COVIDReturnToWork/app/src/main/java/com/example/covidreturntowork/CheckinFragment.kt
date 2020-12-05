package com.example.covidreturntowork

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import android.widget.Button
import android.widget.CheckBox
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class CheckinFragment : AppCompatActivity() {

    private lateinit var buttonSubmit: Button
    private lateinit var checkBox1: CheckBox
    private lateinit var checkBox2: CheckBox
    private lateinit var checkBox3: CheckBox
    private lateinit var checkBox4: CheckBox
    private lateinit var checkBox5: CheckBox
    private lateinit var checkBox6: CheckBox
    private lateinit var checkBox7: CheckBox
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
        var count = 0

        buttonSubmit = findViewById(R.id.submitButton)

        checkBox1 = findViewById(R.id.checkBox1)
        checkBox2 = findViewById(R.id.checkBox2)
        checkBox3 = findViewById(R.id.checkBox3)
        checkBox4 = findViewById(R.id.checkBox4)
        checkBox5 = findViewById(R.id.checkBox5)
        checkBox6 = findViewById(R.id.checkBox6)
        checkBox7 = findViewById(R.id.checkBox7)

        buttonSubmit.setOnClickListener{
            if(checkBox1.isChecked)
                count += 1
            if(checkBox2.isChecked)
                count += 1
            if(checkBox3.isChecked)
                count += 1
            if(checkBox4.isChecked)
                count += 1
            if(checkBox5.isChecked)
                count += 1
            if(checkBox6.isChecked)
                count += 1
            if(checkBox7.isChecked)
                count += 1

            submit(count)
        }
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

    // Submits inputted information
    private fun submit(count: Int){

    }
}