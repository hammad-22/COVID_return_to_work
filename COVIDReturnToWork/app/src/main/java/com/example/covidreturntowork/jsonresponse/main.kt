package com.example.covidreturntowork.jsonresponse

import com.google.gson.Gson
import java.net.URL

fun main(args: Array<String>) {
    val thread = Thread {
        try {
            var response = URL("https://api.covidtracking.com/v1/us/current.json").readText()
            var gson = Gson()

            val data = gson.fromJson(
                response,
                Array<com.example.covidreturntowork.jsonresponse.Response>::class.java
            )

            for(x in 0 until data.size) {
                println("positive increase " + data[x].positiveIncrease)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    thread.start()

}