package org.example

import org.example.Board
import org.example.AsciiRenderer

class MineSweeper(
    val width: Int,
    val height: Int,
    val minesCount: Int,
) {
    fun play() {
        val board = Board.create(
            width = width,
            height = height,
            minesCount = minesCount,
        )
        val game = Game(board)
        val render = AsciiRenderer(game)
        render.render(System.out)

        var input = readAndSplit()
        while (input != null) {
            val result = game.reveal(
                Board.Coordinate(input.second, input.first)
            )
            render.render(System.out)
           
            if (game.isFinish(result)) {
                System.out.println("結果: ${result.toText()}")
                break
            }

            input = readAndSplit()
        }
        
    }

    private fun readAndSplit(): Pair<Int, Int>? {
        println("Type click coordinate as 'y, x' (0 based)> ")

        val input = readln()
        val splited = input.split(",")
        if (splited.size >= 2) {
            return splited[0].toInt() to splited[1].toInt()
        }

        return null
    }
}

fun main() {
    MineSweeper(
        width = 10,
        height = 10,
        minesCount = 5,
    ).play()
}

