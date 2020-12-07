package com.example.covidreturntowork

import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

//this class is modified and based on the Firebase lab in CMSC436 fall 2020
class LoginActivity : AppCompatActivity() {

    private var userEmail: EditText? = null
    private var userPassword: EditText? = null
    private var loginBtn: Button? = null

    private var mAuth: FirebaseAuth? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        //create firebase instance
        mAuth = FirebaseAuth.getInstance()
        userEmail = findViewById(R.id.email)
        userPassword = findViewById(R.id.password)
        loginBtn = findViewById(R.id.login)
        loginBtn!!.setOnClickListener { loginUserAccount() }
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;

    }

    private fun loginUserAccount() {

        val email: String = userEmail?.text.toString()
        val password: String = userPassword?.text.toString()

       //validate email and password based on the validator rule
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(applicationContext, "Please enter email", Toast.LENGTH_LONG).show()
            return
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(applicationContext, "Please enter password", Toast.LENGTH_LONG).show()
            return
        }

        //use firebase authentication to check if login info exist then direct to the home class
        mAuth!!.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(applicationContext, "Login successful", Toast.LENGTH_LONG).show()
                    val intent = Intent(this@LoginActivity, HomeFragment::class.java)
                    startActivity(intent)
                } else {
                    Toast.makeText(
                        applicationContext,
                        "Login failed. Please try again",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

    }

}