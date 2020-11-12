package com.example.covidreturntowork

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders


class History : Fragment() {
    private lateinit var historyInViewModel: HistoryInViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        historyInViewModel = ViewModelProviders.of(this).get(HistoryInViewModel::class.java)
        return inflater.inflate(R.layout.fragment_check_in, container, false)
    }
}