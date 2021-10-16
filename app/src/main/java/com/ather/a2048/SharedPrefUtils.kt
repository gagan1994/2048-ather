package com.ather.a2048

import android.content.Context
import android.content.Context.MODE_PRIVATE

import android.content.SharedPreferences


class SharedPrefUtils(val context: Context) {
    companion object {
        lateinit var _instance: SharedPrefUtils

        val HIGH_SCORE = "high_score"
        fun init(context: Context) {
            _instance = SharedPrefUtils(context)
        }

        fun getInstance(): SharedPrefUtils {
            return _instance
        }
    }

    var sharedPreferences: SharedPreferences

    init {
        sharedPreferences =
            context.getSharedPreferences("2048-pref", MODE_PRIVATE)

    }

    fun getHighScore(): Int {
        return sharedPreferences.getInt(HIGH_SCORE, 0);
    }

    fun updateHighScore(score: Int): Int {
        var currentHighScore = getHighScore()
        if (currentHighScore < score) {
            currentHighScore = score;
            sharedPreferences.edit().putInt(HIGH_SCORE, currentHighScore).apply()
        }
        return currentHighScore;
    }
}