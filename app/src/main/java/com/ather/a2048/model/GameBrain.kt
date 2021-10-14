package com.ather.a2048.model

import kotlin.random.Random


class GameBrain(
    var rowSize: Int = 0,
    var colSize: Int = 0,
) {

    var score: Int = 0;
    lateinit var listener: GameListeners

    var scoreString: String = "0"
        get() = score.toString()

    var gameListener: GameListeners? = null
        get() = if (::listener.isInitialized) listener else null

    var gameObjectsVal: Array<Array<Box>>? = null
        get() = if (::gameObjects.isInitialized) gameObjects else null

    private lateinit var gameObjects: Array<Array<Box>>


    fun initGameObjects() {
        gameObjects = getEmptyGameObjects()


        score = 0;
        resetCanMerge();
        randomEmptyTile();
        randomEmptyTile();

    }

    private fun getEmptyGameObjects(): Array<Array<Box>> {
        return Array(rowSize) { rowIndex ->
            Array(colSize) {
                Box(
                    value = 0,
                    canMerge = false,
                    isNew = false
                )
            }
        }
    }

    private fun randomEmptyTile() {
        // Add empty boxs
        var empty = ArrayList<Box>()
        gameObjects.forEach { rows ->
            empty.addAll(rows.filter { box ->
                box.isEmpty()
            })
        }

        if (empty.isEmpty()) {
            return;
        }
        var rng = Random;
        var index = rng.nextInt(empty.size);
        empty[index].value =
            if (rng.nextInt(8) % 2 == 0) 4 else 2;
        empty[index].isNew = true;
        empty.removeAt(index);
    }

    /*
    * Left swipe
    * */

    fun leftSwipe() {
        if (!canMoveLeft()) {
            gameListener?.cantMove(Direction.LEFT)
            return;
        }

        for (r in 0 until rowSize) {
            for (c in 0 until colSize) {
                mergeLeft(r, c)
            }
        }
        randomEmptyTile()
        resetCanMerge()
    }

    private fun mergeLeft(row: Int, _col: Int) {
        var col = _col
        while (col > 0) {
            merge(gameObjects[row][col], gameObjects[row][col - 1]);
            col--;
        }
    }

    private fun canMoveLeft(): Boolean {
        for (r in 0 until rowSize) {
            for (c in 1 until colSize) {
                if (canMerge(gameObjects[r][c], gameObjects[r][c - 1])) {
                    return true
                }
            }
        }
        return false
    }

    /*
   * Right swipe
   * */

    fun rightSwipe() {
        if (!canMoveRight()) {
            gameListener?.cantMove(Direction.RIGHT)
            return
        }

        for (r in 0 until rowSize) {
            for (c in colSize - 2 downTo 0) {
                mergeRight(r, c)
            }
        }
        randomEmptyTile()
        resetCanMerge()
    }

    private fun canMoveRight(): Boolean {
        for (r in 0 until rowSize) {
            for (c in colSize - 2 downTo 0) {
                if (canMerge(gameObjects[r][c], gameObjects[r][c + 1])) {
                    return true
                }
            }
        }
        return false
    }

    private fun mergeRight(r: Int, _c: Int) {
        var c = _c;
        while (c < colSize - 1) {
            merge(gameObjects[r][c], gameObjects[r][c + 1]);
            c++;
        }
    }


    /*
    * Up swipe
    * */

    fun upSwipe() {
        if (!canMoveUp()) {
            gameListener?.cantMove(Direction.UP)
            return
        }

        for (r in 0 until rowSize) {
            for (c in 0 until colSize) {
                mergeUp(r, c)
            }
        }
        randomEmptyTile()
        resetCanMerge()
    }

    private fun canMoveUp(): Boolean {
        for (r in 1 until rowSize) {
            for (c in 0 until colSize) {
                if (canMerge(gameObjects[r][c], gameObjects[r - 1][c])) {
                    return true
                }
            }
        }
        return false;
    }

    private fun mergeUp(_r: Int, c: Int) {
        var r = _r
        while (r > 0) {
            merge(gameObjects[r][c], gameObjects[r - 1][c]);
            r--;
        }
    }

    /*
      * Down swipe
      * */

    fun downSwipe() {
        if (!canMoveDown()) {
            gameListener?.cantMove(Direction.DOWN)
            return
        }

        for (r in rowSize - 2 downTo 0) {
            for (c in 0 until colSize) {
                mergeDown(r, c)
            }
        }
        randomEmptyTile()
        resetCanMerge()
    }

    fun canMoveDown(): Boolean {
        for (r in rowSize - 2 downTo 0) {
            for (c in 0 until colSize) {
                if (canMerge(gameObjects[r][c], gameObjects[r + 1][c])) {
                    return true
                }
            }
        }
        return false
    }

    fun mergeDown(_r: Int, col: Int) {
        var r = _r
        while (r < rowSize - 1) {
            merge(gameObjects[r][col], gameObjects[r + 1][col])
            r++
        }
    }


    /*
    * Merge
    * */

    fun merge(a: Box, b: Box) {
        if (!canMerge(a, b)) {
            if (!a.isEmpty() && !b.canMerge) {
                b.canMerge = true
            }
            return
        }
        if (b.isEmpty()) {
            b.value = a.value
            a.value = 0
        } else if (a.value == b.value) {
            b.value = b.value * 2
            a.value = 0
            if (score < b.value) {
                score = b.value
            }
            b.canMerge = true
        } else {
            b.canMerge = true
        }
    }

    fun canMerge(a: Box, b: Box): Boolean {
        return !a.canMerge &&
                (b.isEmpty() && !a.isEmpty() || !a.isEmpty() && a.value == b.value)
    }

    private fun resetCanMerge() {
        gameObjects.forEach { rows ->
            rows.forEach { colum ->
                colum.canMerge = false
            }
        }
    }

    fun gameOver(): Boolean {
        return !canMoveLeft() && !canMoveRight() && !canMoveUp() && !canMoveDown();
    }

    fun updateState() {
        if(gameOver()){
            gameListener?.gameOver(score)
        }
    }
}

class Box(
    var value: Int = 0,
    var canMerge: Boolean,
    var isNew: Boolean,
) {


    fun isEmpty(): Boolean {
        return value == 0;
    }

    override fun hashCode(): Int {
        return value.hashCode();
    }

}

interface GameListeners {
    fun gameOver(score:Int)
    fun gameStarted()
    fun cantMove(direction: Direction)
}