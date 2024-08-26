package org.example

import org.example.Board
import org.example.PositiveInt

fun main() {
    val gameBoard = Board.create(
        width = PositiveInt(10),
        height = PositiveInt(10),
        minesCount = PositiveInt(10),
    )

    println(gameBoard)
}
