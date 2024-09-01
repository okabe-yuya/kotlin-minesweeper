package org.example

import org.example.Board
import org.example.AsciiRenderer

class MineSweeper(val board: Board) {
    data class InputPoint(val x: Int, val y: Int) {
        init {
            require(x >= 0) { "x must be require >= 0" }
            require(y >= 0) { "y must be require >= 0" }
        }
    }

    fun play() {
        val game = Game(board)
        val render = AsciiRenderer(game)
        render.render(System.out)

        var input = readAndSplit()
        while (true) {
            val result = game.reveal(
                Board.Coordinate(input.x, input.y)
            )
            render.render(System.out)
           
            if (game.isFinish(result)) {
                System.out.println("結果: ${result.toText()}")
                break
            }

            input = readAndSplit()
        }
        
    }

    private fun readAndSplit(): InputPoint {
        println("🔽 Type click coordinate as 'y, x' (1 based)> ")

        val input = readln()
        val splited = input.split(",")
        if (splited.size >= 2) {
            return InputPoint(splited[1].toInt() - 1, splited[0].toInt() - 1) 
        }

        // 入力が不正な場合、再度、入力を促す
        return readAndSplit()
    }
}

fun selectDifficulty(): Board {
    println("🔽 Select difficulty as 'easy(e), normal(n), hard(h) (default: normal(n))")
    val input = readln()
    return when (input) {
        "e", "easy" -> 
            Board.create(
                width = 5,
                height = 5,
                minesCount = 5,
            )
        "n", "normal" ->
            Board.create(
                width = 10,
                height = 10,
                minesCount = 10,
            )
        "h", "hard" ->
            Board.create(
                width = 25,
                height = 25,
                minesCount = 100,
            )
        else ->
            Board.create(
                width = 10,
                height = 10,
                minesCount = 10,
            )
    }
}

fun main() {
    println("-:::::::::::::::::::::::::::::::::::::::::::-")
    println("-:::::Welcome to terminal MineSweeper💣:::::-")
    println("-:::::::::::::::::::::::::::::::::::::::::::-")
    println("")

    val board = selectDifficulty()
    MineSweeper(board).play()
}

