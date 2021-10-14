package com.ather.a2048.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.ather.a2048.model.TopCards
import androidx.lifecycle.MutableLiveData
import com.ather.a2048.model.GameManager
import com.ather.a2048.view.MainActivity
import com.ather.a2048.model.Direction

class MainActivityViewModel : ViewModel() {
    val scoreValue = MutableLiveData<String>()
    val gameManager = GameManager(MainActivity.SIZE)
    var topCards = TopCards()


    fun swipe(direction: Direction) {
        if (!checkGameStarted()) {
            return
        }
        if (gameManager.gameOver()) {
            return
        }
        when (direction) {
            Direction.LEFT -> {
                gameManager.leftSwipe()
            }
            Direction.RIGHT -> {
                gameManager.rightSwipe()
            }
            Direction.DOWN -> {
                gameManager.downSwipe()
            }
            Direction.UP -> {
                gameManager.upSwipe()
            }
        }
        updateUi();
    }

    fun checkGameStarted(): Boolean {
        // TODO: 13/10/21 Check if game started
        return false;
    }

    private fun updateUi() {
        // TODO: 13/10/21 Update UI score and high score
    }

    fun reset() {
        // TODO: 13/10/21 Reset Vals
        updateUi()
    }

}