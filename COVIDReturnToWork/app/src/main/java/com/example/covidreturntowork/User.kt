package com.example.covidreturntowork

//user class to store user result
data class User(var date: String? = "", var result: String? = "") {
    override fun toString(): String {
        return "Date: " + date + System.lineSeparator() + "Result: " + result

    }
}