package com.example.covidreturntowork

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import androidx.fragment.app.Fragment
import java.lang.StringBuilder
import androidx.lifecycle.Observer
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class CheckinFragment : Fragment() {

    lateinit var pizza: CheckBox
    lateinit var coffee: CheckBox
    lateinit var burger: CheckBox

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_checkin, container, false)
    }
}