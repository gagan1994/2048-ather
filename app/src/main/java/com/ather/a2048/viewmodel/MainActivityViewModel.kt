package com.ather.a2048.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.MutableLiveData

class MainActivityViewModel : ViewModel() {
    val scoreValue = MutableLiveData<String>()

}