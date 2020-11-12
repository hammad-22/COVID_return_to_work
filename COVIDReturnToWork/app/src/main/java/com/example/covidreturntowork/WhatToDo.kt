package com.example.covidreturntowork

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders

class WhatToDo : Fragment() {
    private lateinit var wtdViewModel: WTDViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        wtdViewModel = ViewModelProviders.of(this).get(WTDViewModel::class.java)
        return inflater.inflate(R.layout.fragment_check_in, container, false)
    }
}