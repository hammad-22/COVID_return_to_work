package com.example.covidreturntowork

import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class HistoryFragment : AppCompatActivity() {

    private var mDatabaseReference: DatabaseReference? = null
    private var mUserReference: DatabaseReference? = null
    private var mDatabase: FirebaseDatabase? = null
    private var mAuth: FirebaseAuth? = null

    private lateinit var listViewResults: ListView
    internal lateinit var mAdapter: ArrayAdapter<String>
    internal lateinit var results: MutableList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_history)

        //get current user
        mAuth = FirebaseAuth.getInstance()
        val uid = mAuth!!.currentUser?.uid

        //make database reference to current user data
        mDatabase = FirebaseDatabase.getInstance()
        mDatabaseReference = mDatabase!!.reference
        mUserReference = uid?.let { mDatabaseReference!!.child(it) }

        //display listview result
        listViewResults = findViewById<ListView>(R.id.listViewResults)
        listViewResults.emptyView = findViewById(R.id.textViewMT)
        results = ArrayList()
        mAdapter = ArrayAdapter<String>(this,R.layout.layout_user_list, R.id.textView ,results)
        listViewResults.adapter = mAdapter

        //bottom navigation bar
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigationView.selectedItemId = R.id.navigation_history
        bottomNavigationView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.navigation_home -> {
                    val intent = Intent(this, HomeFragment::class.java)
                    startActivity(intent)
                }
                R.id.navigation_whatToDo -> {
                    val intent = Intent(this, WTDFragment::class.java)
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

        //taking the snapshot of database values and add it to the result array
        //update adapter and listview
        mUserReference!!.addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(dataSnapshot: DataSnapshot, s: String?) {
                val value = dataSnapshot.getValue(User::class.java).toString()
                results.add(0, value)
                mAdapter.notifyDataSetChanged()
            }

            override fun onChildChanged(dataSnapshot: DataSnapshot, s: String?) {}
            override fun onChildRemoved(dataSnapshot: DataSnapshot) {}
            override fun onChildMoved(dataSnapshot: DataSnapshot, s: String?) {}
            override fun onCancelled(databaseError: DatabaseError) {}
        })

        //lock orientation to portrait mode
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

    }
}

