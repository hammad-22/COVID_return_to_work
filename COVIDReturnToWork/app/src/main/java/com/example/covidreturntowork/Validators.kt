package com.example.covidreturntowork

class Validators {
    fun validEmail(email: String?) : Boolean {
        if (email.isNullOrEmpty()) {
            return false
        }

        // General Email Regex (RFC 5322 Official Standard)
        val emailRegex = Regex("(?:[a-z0-9!#\$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#\$%&'" +
                "*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x" +
                "5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z" +
                "0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4" +
                "][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z" +
                "0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|" +
                "\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)])")
        return emailRegex.matches(email)
    }

       // Passwords should contain at least 1 letter,1 number, and one special character
    fun validPassword(password: String?) : Boolean {
        if (password.isNullOrEmpty()) {
            return false
        }

        val passwordRegex = Regex("^(?=.*[0-9])(?=.*[a-zA-Z])(?=\\S+\$).*\$")
        return passwordRegex.matches(password)
    }
}