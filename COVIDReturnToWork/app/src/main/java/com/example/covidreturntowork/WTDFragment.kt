package com.example.covidreturntowork

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.ActivityInfo
import android.graphics.Color
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class WTDFragment : AppCompatActivity() {

    private lateinit var mResult: String
    private lateinit var instructions: TextView
    /*private lateinit var WTD: TextView
    private lateinit var underline: View*/
    private lateinit var firstStep: TextView
    private lateinit var subFirstStep: TextView
    private lateinit var secondStep: TextView
    private lateinit var subSecondStep: TextView
    private lateinit var thirdStep: TextView
    private lateinit var subThirdStep: TextView
    private lateinit var fourthStep: TextView
    private lateinit var subFourthStep: TextView


    private var defaultResult = "No Information Inputted"

    private val sharedPrefFile = "Result"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_wtd)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigationView.selectedItemId = R.id.navigation_whatToDo
        bottomNavigationView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.navigation_home -> {
                    val intent = Intent(this, HomeFragment::class.java)
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
            overridePendingTransition(0,0)
            true
        }

        val sharedPreferences: SharedPreferences = this.getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE)

        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT


        instructions = findViewById(R.id.instructions)
        /*WTD = findViewById(R.id.whatToDo)
        underline = findViewById(R.id.underline)*/
        firstStep = findViewById(R.id.firstStep)
        secondStep = findViewById(R.id.secondStep)
        thirdStep = findViewById(R.id.thirdStep)
        fourthStep = findViewById(R.id.fourthStep)
        subFirstStep = findViewById(R.id.subFirstStep)
        subSecondStep = findViewById(R.id.subSecondStep)
        subThirdStep = findViewById(R.id.subThirdStep)
        subFourthStep = findViewById(R.id.subFourthStep)

        /*firstStep.visibility = View.GONE
        secondStep.visibility = View.GONE
        thirdStep.visibility = View.GONE
        subFirstStep.visibility = View.GONE
        subSecondStep.visibility = View.GONE
        subThirdStep.visibility = View.GONE*/


        /*underline.visibility = View.GONE*/



        if(intent.getStringExtra("Result") != null) {

            mResult = intent.getStringExtra("Result").toString()
            instructions.text = mResult

            updateInfo()

            val editor:SharedPreferences.Editor = sharedPreferences.edit()

            editor.putString("Result", instructions.text.toString())
            editor.apply()
            editor.commit()

        } else {
            if(!(sharedPreferences.getString("Result", "").equals(""))){
                val sharedNameValue = sharedPreferences.getString("Result","")
                instructions.text = sharedNameValue
                updateInfo()
            } else {
                instructions.text = defaultResult

            }
        }


    }

    fun updateInfo(){
        /*WTD.text = "What To Do"
        underline.visibility = View.VISIBLE*/
        if(instructions.text == "Critical"){
            instructions.setTextColor(Color.parseColor("#ff0000"))

            firstStep.text = "Stay home"
            subFirstStep.text = "- Most people with COVID-19 have mild illness and can recover at " +
                    "home without medical care. Do not leave your home, " +
                    "except to get medical care." +
                    "\n- Get rest and stay hydrated. Take over-the-counter medicines, such as acetaminophen." +
                    "\n- Stay in touch with your doctor."

            secondStep.text = "Separate yourself from others"
            subSecondStep.text = "- As much as possible, stay in a specific room and away from other people and pets in your home." +
                    "\n- Tell your close contacts that they may have been exposed to COVID-19."

            thirdStep.text = "Monitor your symptoms"
            subThirdStep.text = "- Follow care instructions from your healthcare provider and local health department."


            fourthStep.text = "When to seek medical attention"
            subFourthStep.text = "If you're experiencing any of the following: " +
                    "\n- Trouble breathing" +
                    "\n- Persistent pain or pressure in the chest" +
                    "\n- Inability to wake up or stay awake" +
                    "\n- Bluish lips or face"


        } else if(instructions.text == "Quarantine"){
            instructions.setTextColor(Color.parseColor("#FFCC00"))
            firstStep.text = "Get tested"
            subFirstStep.text = "- Find your nearest testing site to confirm whether or not you have COVID-19."

            secondStep.text = "Stay home"
            subSecondStep.text = "- Until you receive your test results, do go out to public places." +
                    "\n- Get rest and stay hydrated." +
                    "\n- Stay in touch with your doctor."

            thirdStep.text = "Avoid contact with other people"
            subThirdStep.text = "- Stay in a specific room and away from other people." +
                    "\n- You should wear a mask over your nose and mouth if you must be around other people or pets."

            fourthStep.text = "Clean your hands often"
            subFourthStep.text = "- Wash your hands often with soap and water for at least 20 seconds." +
                    "\n- Use hand sanitizer if soap and water are not available. Use an alcohol-based hand sanitizer with at least 60% alcohol." +
                    "\n- Avoid touching your eyes, nose, and mouth with unwashed hands."


        } else if(instructions.text == "No Symptoms"){
            instructions.setTextColor(Color.parseColor("#000000"))
            firstStep.text = "Get tested"
            subFirstStep.text = "- Find your nearest testing site to confirm whether or not you have COVID-19."

            secondStep.text = "Wear a mask"
            subSecondStep.text = "- You should wear a mask over your nose and mouth if you must be around other people or pets." +
                    "\n- You do not need to wear a mask if you are alone"

            thirdStep.text = "Clean your hands often"
            subThirdStep.text = "- Wash your hands often with soap and water for at least 20 seconds." +
                    "\n- Use hand sanitizer if soap and water are not available. Use an alcohol-based hand sanitizer with at least 60% alcohol." +
                    "\n- Avoid touching your eyes, nose, and mouth with unwashed hands."

        }
    }

    override fun onSaveInstanceState(outState: Bundle){
        outState.putString("Result", instructions.text.toString())
        super.onSaveInstanceState(outState)
    }


    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        defaultResult = savedInstanceState.getString("Result").toString()
        instructions.text = defaultResult
        updateInfo()

       requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

       requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

       requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

    }
}