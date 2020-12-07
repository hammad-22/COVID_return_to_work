package com.example.covidreturntowork

import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
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
    private val mResult = arrayListOf<String>("No Symptoms", "Quarantine", "Critical")

    private lateinit var checkBox1: CheckBox
    private lateinit var checkBox2: CheckBox
    private lateinit var checkBox3: CheckBox
    private lateinit var checkBox4: CheckBox
    private lateinit var checkBox5: CheckBox
    private lateinit var checkBox6: CheckBox
    private lateinit var checkBox7: CheckBox
    private lateinit var checkBox8: CheckBox


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_checkin)

        //access current user with uid
        mAuth = FirebaseAuth.getInstance()
        val uid = mAuth!!.currentUser?.uid
        //getdatabase instance and root reference
        mDatabase = FirebaseDatabase.getInstance()
        mDatabaseReference = mDatabase!!.reference
        //make a database referent to current user
        mUserReference = uid?.let { mDatabaseReference!!.child(it) }

        //bottom navigation bar
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
            //set 0 transition time
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
        checkBox8 = findViewById(R.id.checkBox8)

        buttonSubmit = findViewById(R.id.submitButton)
        buttonSubmit.setOnClickListener { addResult() }

        //lock orientation to portrait mode
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

    }

    //classified the severity of result based of CDC clssification
    // https://www.cdc.gov/coronavirus/2019-ncov/symptoms-testing/symptoms.html
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
        if(checkBox7.isChecked){
            count += 2
        }
        if(checkBox8.isChecked){
            count += 4
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
        val result = findResult()
        val id = mUserReference!!.push().key
        if (id != null) {
            //get current system date
            val date = Calendar.getInstance().time
            //create formatted pattern and convert date to string
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

    //convert system date to string
    fun Date.toString(format: String, locale: Locale = Locale.getDefault()): String {
        val fm = SimpleDateFormat(format, locale)
        return fm.format(this)
    }
}