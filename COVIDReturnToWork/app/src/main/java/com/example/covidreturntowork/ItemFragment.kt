package com.example.covidreturntowork

import android.content.Intent
import android.content.pm.ActivityInfo
import android.graphics.Color
import android.os.Bundle
import android.text.Html
import android.text.method.LinkMovementMethod
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.gson.Gson
import ir.farshid_roohi.linegraph.ChartEntity
import ir.farshid_roohi.linegraph.LineChart
import java.net.URL
import java.util.*
import kotlin.concurrent.schedule

class ItemFragment : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_item)

        val countryCondition = findViewById<TextView>(R.id.countryNew)
        val countryDeath = findViewById<TextView>(R.id.countryDeath)
        val countryLink = this!!.findViewById<TextView>(R.id.countryLink)
        val countryOrder = this!!.findViewById<TextView>(R.id.countryOrder)

        val aboutBtn: Button = findViewById(R.id.button) as Button

        aboutBtn.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                // Launching new Activity on selecting single List Item
                val intent = Intent(this@ItemFragment, HomeFragment::class.java)
                startActivity(intent)
                overridePendingTransition(0,0)
            }
        })

        //bottom navigation bar
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigationView.selectedItemId = R.id.navigation_home
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
            overridePendingTransition(0, 0)
            true
        }


        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        //Thread gets national JSON data
        val thread = Thread {
            try {
                var dateState = URL("https://api.covidtracking.com/v1/us/daily.json").readText()
                var responseUS = URL("https://api.covidtracking.com/v1/us/current.json").readText()
                var gsonst = Gson()
                var gsonus = Gson()

                val dataDate = gsonst.fromJson(
                    dateState,
                    Array<com.example.covidreturntowork.jsonresponse.ResponseDate>::class.java
                )


                val dataUS = gsonus.fromJson(
                    responseUS,
                    Array<com.example.covidreturntowork.jsonresponse.Response>::class.java
                )

                //Adds in data for past week of cases nationally
                val list: MutableList<Float> = java.util.ArrayList()
                val listDates: MutableList<String> = java.util.ArrayList()
                for(x in 0 until 7) {
                    list.add(dataDate[x].positiveIncrease!!.toFloat())
                    var s = dataDate[x].date!!.toString()
                    s = s.substring(4)
                    s = s.substring(0,2) + "-" + s.substring(2)
                    listDates.add(s)
                }

                //makes the line graph
                //Used a library from online but still add coded to it myself for my custom graph
                val lineChart: LineChart = findViewById<LineChart>(R.id.lineChart)

                list.reverse()
                listDates.reverse()
                val firstChartEntity = ChartEntity(Color.WHITE, list.toFloatArray())


                val list2 = java.util.ArrayList<ChartEntity>().apply {
                    add(firstChartEntity)

                }

                lineChart.setList(list2)

                val listDate: MutableList<String> = java.util.ArrayList()
                for (text in listDates) {
                    listDate.add(text)
                }
                lineChart.setLegend(listDate)

                //Adds in JSON data for each case nationally
                for(x in 0 until dataUS.size) {
                    countryCondition.setText("" + dataUS[x].positiveIncrease + " new cases")
                    countryDeath.setText("" + dataUS[x].deathIncrease + " new deaths")
                    if(dataUS[x].positiveIncrease!! > 0) {
                        countryOrder.setText("Unsafe \uD83D\uDE37")
                    }

                }


            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        //Creates link
        countryLink.setText(Html.fromHtml("<a href=https://www.cdc.gov/coronavirus/2019-ncov/index.html>CDC Info</a>"))

        countryLink.setMovementMethod(LinkMovementMethod.getInstance())

        Timer("SettingUp", false).schedule(500) {
            thread.start()
        }


    }
}