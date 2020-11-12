package com.example.covidreturntowork

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HistoryInViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is History view fragment"
    }
    val text: LiveData<String> = _text
}