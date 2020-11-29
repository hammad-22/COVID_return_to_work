package com.example.covidreturntowork

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView


class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigationView.setOnNavigationItemSelectedListener{
            var selectedFragment: Fragment? = null
            when(it.itemId) {
                R.id.navigation_home -> {
                    selectedFragment = HomeFragment()
                }
                R.id.navigation_whatToDo -> {
                    selectedFragment = WTDFragment()
                }
                R.id.navigation_history -> {
                    selectedFragment = HistoryFragment()
                }
                R.id.navigation_logout -> {
                    selectedFragment = LogoutFragment()
                }
                R.id.navigation_checkIn -> {
                    selectedFragment = CheckinFragment()
                }
            }

            if (selectedFragment != null) {
                supportFragmentManager.beginTransaction().replace(
                    R.id.fragment_container,
                    selectedFragment
                ).commit()
            };

            true
        }

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().replace(
                R.id.fragment_container,
                HomeFragment()
            ).commit()
        }
    }




}