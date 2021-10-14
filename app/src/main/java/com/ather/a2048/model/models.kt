package com.ather.a2048.model


class TopCards{
    fun updateSwipe() {
        swipeCount++;
    }

    private var swipeCount = 0
    private var scoreCount = 0
    private var bestCount = 0

    var scoreString: String = "0"
        get() = scoreCount.toString()
    var swipeCountString: String = "0"
        get() = swipeCount.toString()
    var bestCountString: String = "0"
        get() = bestCount.toString()


}