package com.example.covidreturntowork

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders


class CheckIn : Fragment() {

    private lateinit var checkInViewModel: CheckInViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        checkInViewModel =
            ViewModelProviders.of(this).get(CheckInViewModel::class.java)
        return inflater.inflate(R.layout.fragment_check_in, container, false)
    }
}