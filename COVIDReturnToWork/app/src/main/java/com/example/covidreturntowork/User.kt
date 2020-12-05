package com.example.covidreturntowork

data class User(var date: String? = "", var result: String? = "") {
    override fun toString(): String {
        return "Date: " + date + System.lineSeparator() + "Result: " + result

    }
}