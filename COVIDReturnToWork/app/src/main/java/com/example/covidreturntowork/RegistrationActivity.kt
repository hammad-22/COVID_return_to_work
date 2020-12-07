package com.example.covidreturntowork

import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class RegistrationActivity : AppCompatActivity() {

    private var emailTV: EditText? = null
    private var passwordTV: EditText? = null
    private var regBtn: Button? = null

    private var validator = Validators()
    private var mAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        //get firebase authentication instance
        mAuth = FirebaseAuth.getInstance()
        emailTV = findViewById(R.id.email)
        passwordTV = findViewById(R.id.password)
        regBtn = findViewById(R.id.register)

        regBtn!!.setOnClickListener { registerNewUser() }

        //lock orientation to portrait
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;

    }

    private fun registerNewUser() {

        val email: String = emailTV!!.text.toString()
        val password: String = passwordTV!!.text.toString()

        //validate email and password based on the validator rule
        if (!validator.validEmail(email)) {
            Toast.makeText(applicationContext, "Please enter a valid email", Toast.LENGTH_LONG).show()
            return
        }
        if (!validator.validPassword(password)) {
            Toast.makeText(applicationContext, "Please enter a valid password", Toast.LENGTH_LONG).show()
            return
        }

        //use firebase authentication to create new user
        mAuth!!.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(applicationContext, "Registration successful", Toast.LENGTH_LONG).show()
                        val intent = Intent(this@RegistrationActivity, LoginActivity::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(applicationContext, "Registration failed. Please try again", Toast.LENGTH_LONG).show()
                    }
                }
    }
}
