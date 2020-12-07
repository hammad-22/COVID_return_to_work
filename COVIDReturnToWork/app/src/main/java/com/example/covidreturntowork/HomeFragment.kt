package com.example.covidreturntowork

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.text.Html
import android.text.method.LinkMovementMethod
import android.view.View
import android.widget.*
import android.widget.AdapterView.OnItemSelectedListener
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.gson.Gson
import ir.farshid_roohi.linegraph.ChartEntity
import ir.farshid_roohi.linegraph.LineChart
import java.net.URL
import java.util.*


class HomeFragment : AppCompatActivity() {
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_home)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigationView.selectedItemId = R.id.navigation_home
        bottomNavigationView.setOnNavigationItemSelectedListener{
            when(it.itemId) {
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
            overridePendingTransition(0, 0)
            true
        }
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        val states: MutableMap<String, String> = HashMap()
        states["Alabama"] = "AL"
        states["Alaska"] = "AK"
        states["Arizona"] = "AZ"
        states["Arkansas"] = "AR"
        states["California"] = "CA"
        states["Colorado"] = "CO"
        states["Connecticut"] = "CT"
        states["Delaware"] = "DE"
        states["Florida"] = "FL"
        states["Georgia"] = "GA"
        states["Hawaii"] = "HI"
        states["Idaho"] = "ID"
        states["Illinois"] = "IL"
        states["Indiana"] = "IN"
        states["Iowa"] = "IA"
        states["Kansas"] = "KS"
        states["Kentucky"] = "KY"
        states["Louisiana"] = "LA"
        states["Maine"] = "ME"
        states["Maryland"] = "MD"
        states["Massachusetts"] = "MA"
        states["Michigan"] = "MI"
        states["Minnesota"] = "MN"
        states["Mississippi"] = "MS"
        states["Missouri"] = "MO"
        states["Montana"] = "MT"
        states["Nebraska"] = "NE"
        states["Nevada"] = "NV"
        states["New Hampshire"] = "NH"
        states["New Jersey"] = "NJ"
        states["New Mexico"] = "NM"
        states["New York"] = "NY"
        states["North Carolina"] = "NC"
        states["North Dakota"] = "ND"
        states["Ohio"] = "OH"
        states["Oklahoma"] = "OK"
        states["Oregon"] = "OR"
        states["Pennsylvania"] = "PA"
        states["Rhode Island"] = "RI"
        states["South Carolina"] = "SC"
        states["South Dakota"] = "SD"
        states["Tennessee"] = "TN"
        states["Texas"] = "TX"
        states["Utah"] = "UT"
        states["Vermont"] = "VT"
        states["Virginia"] = "VA"
        states["Washington"] = "WA"
        states["West Virginia"] = "WV"
        states["Wisconsin"] = "WI"
        states["Wyoming"] = "WY"

        val textView2 = findViewById<TextView>(R.id.textView2)
        val textView3 = findViewById<TextView>(R.id.textView3)
        val stateCondition = findViewById<TextView>(R.id.stateOrder)
        val stateLink = findViewById<TextView>(R.id.stateLink)

        val spinner: Spinner = findViewById(R.id.states_spinner)
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter.createFromResource(
            this,
            R.array.states_array,
            R.layout.custom_xml_spinner_layout
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinner.adapter = adapter
        }
        val mySpinner = findViewById<Spinner>(R.id.states_spinner)


        var stateName = "Maryland"

        val aboutBtn: Button = findViewById(R.id.button) as Button

        //goes to page for national data
        aboutBtn.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                // Launching new Activity on selecting single List Item
                val intent = Intent(this@HomeFragment, ItemFragment::class.java)
                startActivity(intent)
                overridePendingTransition(0,0)
            }
        })

        //sets default value for spinner
        val compareValue = stateName
        val adapter = ArrayAdapter.createFromResource(
            this,
            R.array.states_array,
            R.layout.custom_xml_spinner_layout
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        mySpinner.setAdapter(adapter)
        if (compareValue != null) {
            val spinnerPosition = adapter.getPosition(compareValue)
            mySpinner.setSelection(spinnerPosition)
        }

        //this thread gets all the JSON data and fills up the page
        val thread = Thread {
            try {
                var dateState = URL("https://api.covidtracking.com/v1/states/" + states[stateName] + "/daily.json").readText()
                var responseST = URL("https://api.covidtracking.com/v1/states/current.json").readText()
                var responseMETA = URL("https://api.covidtracking.com/v1/states/info.json").readText()
                var gsonst = Gson()
                var gsonus = Gson()

                val dataDate = gsonst.fromJson(
                    dateState,
                    Array<com.example.covidreturntowork.jsonresponse.ResponseDateState>::class.java
                )
                val dataST = gsonst.fromJson(
                    responseST,
                    Array<com.example.covidreturntowork.jsonresponse.ResponseState>::class.java
                )

                val meta = gsonus.fromJson(
                    responseMETA,
                    Array<com.example.covidreturntowork.jsonresponse.ResponseMeta>::class.java
                )
                //adds in data for past week of cases
                val list: MutableList<Float> = ArrayList()
                val listDates: MutableList<String> = ArrayList()
                for(x in 0 until 7) {
                    list.add(dataDate[x].positiveIncrease!!.toFloat())
                    var s = dataDate[x].date!!.toString()
                    s = s.substring(4)
                    s = s.substring(0, 2) + "-" + s.substring(2)
                    listDates.add(s)
                }

                //this makes the graph
                //Graph library was available online for use
                val lineChart: LineChart = findViewById<LineChart>(R.id.lineChart)

                list.reverse()
                listDates.reverse()
                val firstChartEntity = ChartEntity(Color.WHITE, list.toFloatArray())


                val list2 = ArrayList<ChartEntity>().apply {
                    add(firstChartEntity)

                }

                lineChart.setList(list2)

                val listDate: MutableList<String> = ArrayList()
                for (text in listDates) {
                    listDate.add(text)
                }
                lineChart.setLegend(listDate)

                //this adds in the data for each state
                for(x in 0 until dataST.size) {
                    if(dataST[x].state == states[stateName]) {
                        textView2.setText("" + dataST[x].positiveIncrease + " new cases")
                        textView3.setText("" + dataST[x].deathIncrease + " new deaths")
                        if(dataST[x].positiveIncrease!! > 0) {
                            stateCondition.setText("Unsafe \uD83D\uDE37")
                        }
                        stateLink.setText(Html.fromHtml("<a href=" + meta[x].covid19Site + ">More State Info</a>"))
                    }

                }
            } catch (e: Exception) {
                println("err")
            }
        }

        stateLink.setMovementMethod(LinkMovementMethod.getInstance())

        thread.start()

    }

    //onStart used for spinner since onCreate will have default data setup for Home page
    override fun onStart() {
        super.onStart()

        val states: MutableMap<String, String> = HashMap()
        states["Alabama"] = "AL"
        states["Alaska"] = "AK"
        states["Arizona"] = "AZ"
        states["Arkansas"] = "AR"
        states["California"] = "CA"
        states["Colorado"] = "CO"
        states["Connecticut"] = "CT"
        states["Delaware"] = "DE"
        states["Florida"] = "FL"
        states["Georgia"] = "GA"
        states["Hawaii"] = "HI"
        states["Idaho"] = "ID"
        states["Illinois"] = "IL"
        states["Indiana"] = "IN"
        states["Iowa"] = "IA"
        states["Kansas"] = "KS"
        states["Kentucky"] = "KY"
        states["Louisiana"] = "LA"
        states["Maine"] = "ME"
        states["Maryland"] = "MD"
        states["Massachusetts"] = "MA"
        states["Michigan"] = "MI"
        states["Minnesota"] = "MN"
        states["Mississippi"] = "MS"
        states["Missouri"] = "MO"
        states["Montana"] = "MT"
        states["Nebraska"] = "NE"
        states["Nevada"] = "NV"
        states["New Hampshire"] = "NH"
        states["New Jersey"] = "NJ"
        states["New Mexico"] = "NM"
        states["New York"] = "NY"
        states["North Carolina"] = "NC"
        states["North Dakota"] = "ND"
        states["Ohio"] = "OH"
        states["Oklahoma"] = "OK"
        states["Oregon"] = "OR"
        states["Pennsylvania"] = "PA"
        states["Rhode Island"] = "RI"
        states["South Carolina"] = "SC"
        states["South Dakota"] = "SD"
        states["Tennessee"] = "TN"
        states["Texas"] = "TX"
        states["Utah"] = "UT"
        states["Vermont"] = "VT"
        states["Virginia"] = "VA"
        states["Washington"] = "WA"
        states["West Virginia"] = "WV"
        states["Wisconsin"] = "WI"
        states["Wyoming"] = "WY"

        val textView2 = findViewById<TextView>(R.id.textView2)
        val textView3 = findViewById<TextView>(R.id.textView3)
        val stateCondition = findViewById<TextView>(R.id.stateOrder)
        val stateLink = findViewById<TextView>(R.id.stateLink)

        //Helps change data and pulls in new data for different state selected from spinner
        var spinner = findViewById<Spinner>(R.id.states_spinner)
        spinner.setOnItemSelectedListener(object : OnItemSelectedListener {
            override fun onItemSelected(
                parentView: AdapterView<*>?,
                selectedItemView: View,
                position: Int,
                id: Long
            ) {
                val thread = Thread {
                    try {
                        val keyList = ArrayList(states.values)
                        keyList.sort()
                        var dateState = URL("https://api.covidtracking.com/v1/states/" + keyList[position] + "/daily.json").readText()
                        var responseST = URL("https://api.covidtracking.com/v1/states/current.json").readText()
                        var responseMETA = URL("https://api.covidtracking.com/v1/states/info.json").readText()
                        var gsonst = Gson()
                        var gsonus = Gson()

                        val dataDate = gsonst.fromJson(
                            dateState,
                            Array<com.example.covidreturntowork.jsonresponse.ResponseDateState>::class.java
                        )
                        val dataST = gsonst.fromJson(
                            responseST,
                            Array<com.example.covidreturntowork.jsonresponse.ResponseState>::class.java
                        )

                        val meta = gsonus.fromJson(
                            responseMETA,
                            Array<com.example.covidreturntowork.jsonresponse.ResponseMeta>::class.java
                        )
                        val list: MutableList<Float> = ArrayList()
                        val listDates: MutableList<String> = ArrayList()
                        for(x in 0 until 7) {
                            list.add(dataDate[x].positiveIncrease!!.toFloat())
                            var s = dataDate[x].date!!.toString()
                            s = s.substring(4)
                            s = s.substring(0, 2) + "-" + s.substring(2)
                            listDates.add(s)
                        }

                        val lineChart: LineChart = findViewById<LineChart>(R.id.lineChart)

                        list.reverse()
                        listDates.reverse()
                        val firstChartEntity = ChartEntity(Color.WHITE, list.toFloatArray())


                        val list2 = ArrayList<ChartEntity>().apply {
                            add(firstChartEntity)

                        }

                        lineChart.setList(list2)

                        val listDate: MutableList<String> = ArrayList()
                        for (text in listDates) {
                            listDate.add(text)
                        }
                        lineChart.setLegend(listDate)

                        for(x in 0 until dataST.size) {
                            if(dataST[x].state == keyList[position]) {
                                textView2.setText("" + dataST[x].positiveIncrease + " new cases")
                                textView3.setText("" + dataST[x].deathIncrease + " new deaths")
                                if(dataST[x].positiveIncrease!! > 0) {
                                    stateCondition.setText("Unsafe \uD83D\uDE37")
                                }
                                stateLink.setText(Html.fromHtml("<a href=" + meta[x].covid19Site + ">More State Info</a>"))

                            }

                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }

                //Creates link
                stateLink.setMovementMethod(LinkMovementMethod.getInstance())

                thread.start()
            }

            override fun onNothingSelected(parentView: AdapterView<*>?) {
                // your code here
            }
        })

    }


}
