package org.example

import java.io.PrintStream

class AsciiRenderer(val grid: Game) {
    fun render(output: PrintStream) {
        val height = grid.board.height
        val width = grid.board.width

        (0 until height).forEach { y ->
            (0 until width).forEach { x ->
                val cell = grid.cell(Board.Coordinate(x, y))
                val content = when (cell) {
                    null -> "#"
                    is Cell.Mine -> "*"
                    is Cell.Empty -> {
                        if (cell.neighbourMines == 0) "-" else cell.neighbourMines
                    }
                }
                output.print(content)
            }
            output.println("") 
        }
    }
}

