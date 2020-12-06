package com.example.covidreturntowork

import android.content.Intent
import android.content.pm.ActivityInfo
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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.text.SimpleDateFormat
import java.util.*


class CheckinFragment : AppCompatActivity() {
    private var mDatabaseReference: DatabaseReference? = null
    private var mUserReference: DatabaseReference? = null
    private var mDatabase: FirebaseDatabase? = null
    private var mAuth: FirebaseAuth? = null

    private lateinit var buttonSubmit: Button
    private val mResult = arrayListOf<String>("no symptoms", "quarantine", "critical")

    private lateinit var checkBox1: CheckBox
    private lateinit var checkBox2: CheckBox
    private lateinit var checkBox3: CheckBox
    private lateinit var checkBox4: CheckBox
    private lateinit var checkBox5: CheckBox
    private lateinit var checkBox6: CheckBox
    private lateinit var checkBox7: CheckBox


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_checkin)

        mAuth = FirebaseAuth.getInstance()
        val uid = mAuth!!.currentUser?.uid
        mDatabase = FirebaseDatabase.getInstance()
        mDatabaseReference = mDatabase!!.reference
        mUserReference = uid?.let { mDatabaseReference!!.child(it) }

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigationView.selectedItemId = R.id.navigation_checkIn
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
            }
            overridePendingTransition(0,0)
            true
        }

        checkBox1 = findViewById(R.id.checkBox1)
        checkBox2 = findViewById(R.id.checkBox2)
        checkBox3 = findViewById(R.id.checkBox3)
        checkBox4 = findViewById(R.id.checkBox4)
        checkBox5 = findViewById(R.id.checkBox5)
        checkBox6 = findViewById(R.id.checkBox6)
        checkBox7 = findViewById(R.id.checkBox7)

        buttonSubmit = findViewById(R.id.submitButton)
        buttonSubmit.setOnClickListener { addResult() }
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

    }

    private fun findResult():String{
        var count = 0
        if(checkBox1.isChecked){
            count += 3
        }
        if(checkBox2.isChecked){
            count += 10
        }
        if(checkBox3.isChecked){
            count += 10
        }
        if(checkBox4.isChecked){
            count += 2
        }
        if(checkBox5.isChecked){
            count += 10
        }
        if(checkBox6.isChecked){
            count += 1
        }
        if(checkBox1.isChecked){
            count += 2
        }
        var toReturn: String


        if(count == 0){
            toReturn = mResult[0]
        } else if(count in 1..5){
            toReturn = mResult[1]
        } else {
            toReturn = mResult[2]
        }
        return toReturn
    }

    // Add result to database
    private fun addResult() {
        //TODO change result to real function that call result
        val result = findResult()
        val id = mUserReference!!.push().key
        if (id != null) {
            val date = getCurrentDateTime()
            val dateInString = date.toString("yyyy/MM/dd HH:mm:ss")
            val user = User(dateInString, result)
            mUserReference!!.child(id).setValue(user)
            Toast.makeText(this, "Result Added", Toast.LENGTH_LONG)

        }

        val intent = Intent(this, WTDFragment::class.java)
        intent.putExtra("Result", result)
        startActivity(intent)
        overridePendingTransition(0,0)
    }

    fun Date.toString(format: String, locale: Locale = Locale.getDefault()): String {
        val formatter = SimpleDateFormat(format, locale)
        return formatter.format(this)
    }

    fun getCurrentDateTime(): Date {
        return Calendar.getInstance().time
    }
}