package org.example

import java.io.PrintStream

class AsciiRenderer(val grid: Game) {
    fun render(output: PrintStream) {
        val height = grid.board.height.value
        val width = grid.board.width.value

        (0 until height).forEach { y ->
            (0 until width).forEach { x ->
                val coordinate = Board.Coordinate(
                    x = PositiveInt(x),
                    y = PositiveInt(y),
                )
                val cell = grid.cell(coordinate)
                val content = when (cell) {
                    null -> "#"
                    is Cell.Mine -> "*"
                    is Cell.Empty -> {
                        if (cell.neighbourMines.value == 0) "-" else cell.neighbourMines.value
                    }
                }
                output.print(content)
            }
            output.println("") 
        }
    }
}

