package com.ather.a2048

import android.content.Context
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.core.graphics.ColorUtils
import com.ather.a2048.model.Box
import com.google.gson.Gson

class Utils {
    companion object {
        fun log(message: String) {
            Log.i("Gagan", message)
        }

        fun log(message: String, grids: Array<Array<Int>>) {
            var text = message + "\n";

            for (i in 0 until grids.size) {
                text = text + "[ "
                for (j in 0 until grids[i].size) {
                    text = grids[i][j].toString() + ", " + text;
                }
                text = text + " ]\n"
            }
            log(text)
        }

        fun log(message: Array<Array<Box>>) {
            Log.i("Gagan", Gson().toJson(message))
        }

        fun getColor(value: Int): Int {
            when (value) {
                0 -> {
                    return R.color.box_color
                }
                2 -> {
                    return R.color.box_color_2
                }
                4 -> {
                    return R.color.box_color_4
                }
                8 -> {
                    return R.color.box_color_8
                }
                16 -> {
                    return R.color.box_color_16
                }
                32 -> {
                    return R.color.box_color_32
                }
                64 -> {
                    return R.color.box_color_64
                }
                128 -> {
                    return R.color.box_color_128
                }
                256 -> {
                    return R.color.box_color_256
                }
                512 -> {
                    return R.color.box_color_512
                }
                1024 -> {
                    return R.color.box_color_1024
                }
                2048 -> {
                    return R.color.box_color_2048
                }
                else -> {
                    return R.color.box_color_super
                }
            }
        }

    }
}