package com.ather.a2048.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.MutableLiveData
import com.ather.a2048.SharedPrefUtils
import com.ather.a2048.model.*
import com.ather.a2048.view.MainActivity

class MainActivityViewModel : ViewModel() {
    val scoreValue = MutableLiveData<String>()
    val highScore = MutableLiveData<String>()
    var gameManager = GameBrain(rowSize = MainActivity.SIZE, colSize = MainActivity.SIZE)
    var gridData = MutableLiveData<Array<Array<Box>>>()


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
        return gameManager.gameObjectsVal != null
    }

    private fun updateUi() {
        scoreValue.postValue(gameManager.scoreString)
        gridData.postValue(gameManager.gameObjectsVal)
        highScore.postValue(SharedPrefUtils.getInstance()
            .updateHighScore(gameManager.score).toString())
        gameManager.updateState()
    }

    fun reset(listeners: GameListeners) {
        gameManager.initGameObjects()
        gameManager.listener = listeners
        updateUi()
    }

    fun updateHighScore() {
        highScore.postValue(SharedPrefUtils.getInstance().getHighScore().toString())
    }

}