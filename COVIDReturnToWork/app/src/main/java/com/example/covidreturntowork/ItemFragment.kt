package com.example.covidreturntowork

import android.R.array
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
import kotlin.collections.ArrayList


/**
 * A fragment representing a list of Items.
 */
class ItemFragment : AppCompatActivity() {


    private val graph1 = floatArrayOf(
        113000.0F,
        183000f,
        188000f,
        695000f,
        324000f,
        230000f,
        188000f,
        15000f,
        126000f,
        5000f,
        33000f
    )
    private val graph2 = floatArrayOf(
        0f,
        245000f,
        1011000f,
        1000f,
        0f,
        0f,
        47000f,
        20000f,
        12000f,
        124400f,
        160000f
    )
    val legendArr = arrayOf(
        "05/21",
        "05/22",
        "05/23",
        "05/24",
        "05/25",
        "05/26",
        "05/27",
        "05/28",
        "05/29",
        "05/30",
        "05/31"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_item)

        val lineChart:LineChart = findViewById<LineChart>(R.id.lineChart)
        val countryCondition = findViewById<TextView>(R.id.countryNew)
        val countryDeath = findViewById<TextView>(R.id.countryDeath)
        val countryLink = this!!.findViewById<TextView>(R.id.countryLink)
        val countryOrder = this!!.findViewById<TextView>(R.id.countryOrder)


        val firstChartEntity = ChartEntity(Color.WHITE, graph1)
        val secondChartEntity = ChartEntity(Color.YELLOW, graph2)

        val list = ArrayList<ChartEntity>().apply {
            add(firstChartEntity)
            add(secondChartEntity)
        }
        val list2: MutableList<String> = ArrayList()
        for (text in legendArr) {
            list2.add(text)
        }
        lineChart.setLegend(list2)
        lineChart.setList(list)

        val aboutBtn: Button = findViewById(R.id.button) as Button

        aboutBtn.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                // Launching new Activity on selecting single List Item
                val intent = Intent(this@ItemFragment, HomeFragment::class.java)
                startActivity(intent)
            }
        })



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

                val list: MutableList<Float> = java.util.ArrayList()
                val listDates: MutableList<String> = java.util.ArrayList()
                for(x in 0 until 7) {
                    list.add(dataDate[x].positiveIncrease!!.toFloat())
                    var s = dataDate[x].date!!.toString()
                    s = s.substring(4)
                    s = s.substring(0,2) + "-" + s.substring(2)
                    listDates.add(s)
                }

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

        countryLink.setText(Html.fromHtml("<a href=https://www.cdc.gov/coronavirus/2019-ncov/index.html>CDC Info</a>"))

        countryLink.setMovementMethod(LinkMovementMethod.getInstance())

        thread.start()

    }
}