package com.example.covidreturntowork

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.net.Uri
import android.os.Bundle
import android.text.Html
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.gson.Gson
import java.net.URL
import java.util.*


class HomeFragment : Fragment() {

    private lateinit var fusedLocationClient: FusedLocationProviderClient



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val states: MutableMap<String, String> = HashMap()
        states["Alabama"] = "AL"
        states["Alaska"] = "AK"
        states["Alberta"] = "AB"
        states["American Samoa"] = "AS"
        states["Arizona"] = "AZ"
        states["Arkansas"] = "AR"
        states["Armed Forces (AE)"] = "AE"
        states["Armed Forces Americas"] = "AA"
        states["Armed Forces Pacific"] = "AP"
        states["British Columbia"] = "BC"
        states["California"] = "CA"
        states["Colorado"] = "CO"
        states["Connecticut"] = "CT"
        states["Delaware"] = "DE"
        states["District Of Columbia"] = "DC"
        states["Florida"] = "FL"
        states["Georgia"] = "GA"
        states["Guam"] = "GU"
        states["Hawaii"] = "HI"
        states["Idaho"] = "ID"
        states["Illinois"] = "IL"
        states["Indiana"] = "IN"
        states["Iowa"] = "IA"
        states["Kansas"] = "KS"
        states["Kentucky"] = "KY"
        states["Louisiana"] = "LA"
        states["Maine"] = "ME"
        states["Manitoba"] = "MB"
        states["Maryland"] = "MD"
        states["Massachusetts"] = "MA"
        states["Michigan"] = "MI"
        states["Minnesota"] = "MN"
        states["Mississippi"] = "MS"
        states["Missouri"] = "MO"
        states["Montana"] = "MT"
        states["Nebraska"] = "NE"
        states["Nevada"] = "NV"
        states["New Brunswick"] = "NB"
        states["New Hampshire"] = "NH"
        states["New Jersey"] = "NJ"
        states["New Mexico"] = "NM"
        states["New York"] = "NY"
        states["Newfoundland"] = "NF"
        states["North Carolina"] = "NC"
        states["North Dakota"] = "ND"
        states["Northwest Territories"] = "NT"
        states["Nova Scotia"] = "NS"
        states["Nunavut"] = "NU"
        states["Ohio"] = "OH"
        states["Oklahoma"] = "OK"
        states["Ontario"] = "ON"
        states["Oregon"] = "OR"
        states["Pennsylvania"] = "PA"
        states["Prince Edward Island"] = "PE"
        states["Puerto Rico"] = "PR"
        states["Quebec"] = "QC"
        states["Rhode Island"] = "RI"
        states["Saskatchewan"] = "SK"
        states["South Carolina"] = "SC"
        states["South Dakota"] = "SD"
        states["Tennessee"] = "TN"
        states["Texas"] = "TX"
        states["Utah"] = "UT"
        states["Vermont"] = "VT"
        states["Virgin Islands"] = "VI"
        states["Virginia"] = "VA"
        states["Washington"] = "WA"
        states["West Virginia"] = "WV"
        states["Wisconsin"] = "WI"
        states["Wyoming"] = "WY"
        states["Yukon Territory"] = "YT"

        var latitude = 0.0
        var longitude = 0.0
        var inf = inflater.inflate(R.layout.fragment_home, container, false)
        val textView2 = inf!!.findViewById<TextView>(R.id.textView2)
        val textView3 = inf!!.findViewById<TextView>(R.id.textView3)
        val state = inf!!.findViewById<TextView>(R.id.state)
        val stateCondition = inf!!.findViewById<TextView>(R.id.stateOrder)
        val countryCondition = inf!!.findViewById<TextView>(R.id.countryNew)
        val countryDeath = inf!!.findViewById<TextView>(R.id.countryDeath)
        val stateLink = inf!!.findViewById<TextView>(R.id.stateLink)
        val countryLink = inf!!.findViewById<TextView>(R.id.countryLink)
        val countryOrder = inf!!.findViewById<TextView>(R.id.countryOrder)
        var stateName = "Maryland"

        val permissions = arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION)
        activity?.let { ActivityCompat.requestPermissions(it, permissions, 0) }

        fusedLocationClient = activity?.let { LocationServices.getFusedLocationProviderClient(it) }!!


        @SuppressLint("MissingPermission")
        fun obtieneLocalizacion(){



            if (ActivityCompat.checkSelfPermission(
                    activity!!,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    activity!!,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return
            }

            println("im in")
            fusedLocationClient.lastLocation
                .addOnSuccessListener { location: Location? ->
                    latitude = location?.latitude!!
                    longitude = location?.longitude
                    val geocoder = Geocoder(activity, Locale.getDefault())
                    val addresses: List<Address> = geocoder.getFromLocation(latitude, longitude, 1)
                    stateName = addresses[0].adminArea
                    state.setText(stateName+ " Daily Change")
                }
        }
        obtieneLocalizacion()

        val thread = Thread {
            try {
                println(states[stateName])
                var responseST = URL("https://api.covidtracking.com/v1/states/current.json").readText()
                var responseUS = URL("https://api.covidtracking.com/v1/us/current.json").readText()
                var responseMETA = URL("https://api.covidtracking.com/v1/states/info.json").readText()
                var gsonst = Gson()
                var gsonus = Gson()


                val dataST = gsonst.fromJson(
                    responseST,
                    Array<com.example.covidreturntowork.jsonresponse.ResponseState>::class.java
                )

                val dataUS = gsonus.fromJson(
                    responseUS,
                    Array<com.example.covidreturntowork.jsonresponse.Response>::class.java
                )
                val meta = gsonus.fromJson(
                    responseMETA,
                    Array<com.example.covidreturntowork.jsonresponse.ResponseMeta>::class.java
                )

                for(x in 0 until dataUS.size) {
                    countryCondition.setText("" + dataUS[x].positiveIncrease + " new cases")
                    countryDeath.setText("" + dataUS[x].deathIncrease + " new deaths")
                    if(dataUS[x].positiveIncrease!! > 0) {
                        countryOrder.setText("Unsafe \uD83D\uDE37")
                    }

                }

                for(x in 0 until dataST.size) {
                    if(dataST[x].state == states[stateName]) {

                        textView2.setText("" + dataST[x].positiveIncrease + " new cases")
                        textView3.setText("" + dataST[x].deathIncrease + " new deaths")
                        if(dataST[x].positiveIncrease!! > 0) {
                            stateCondition.setText("Unsafe \uD83D\uDE37")
                        }
                        stateLink.setText( Html.fromHtml("<a href="+meta[x].covid19Site +">More State Info</a>"))


                    }

                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        countryLink.setText(Html.fromHtml("<a href=https://www.cdc.gov/coronavirus/2019-ncov/index.html>CDC Info</a>"))
        stateLink.setMovementMethod(LinkMovementMethod.getInstance())
        countryLink.setMovementMethod(LinkMovementMethod.getInstance())

        thread.start()

        return inf
    }

}