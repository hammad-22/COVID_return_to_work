package com.example.covidreturntowork

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer

class HomeFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        var inf = inflater.inflate(R.layout.fragment_home, container, false)
        val textView = inf!!.findViewById<TextView>(R.id.textView2)
        textView.setText(jsonData())
        return inf
    }

    fun jsonData(): String {
        return "hello"
    }





}