package com.ather.a2048.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ather.a2048.model.TopCards

class MainActivityViewModel : ViewModel() {

    var topCards = TopCards()
    val scoreValue = MutableLiveData<String>()
    val swipeValue = MutableLiveData<String>()
    val bestValue = MutableLiveData<String>()


    fun onLeftSwipe() {

    }

    fun onRightSwipe() {

    }

    fun onUpSwipe() {

    }

    fun onDownSwipe() {

    }

    fun updateSwipes() {
        topCards.updateSwipe()
        updateUi();
    }

    private fun updateUi() {
        swipeValue.postValue(topCards.swipeCountString)
        scoreValue.postValue(topCards.scoreString)
        bestValue.postValue(topCards.bestCountString)
    }

    fun reset() {
        topCards = TopCards()
    }

}