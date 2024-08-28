package org.example

val CELL_WIDTH_NO_ADJACENT_MINES = Cell.Empty(PositiveInt(0))

enum class GameCommand {
    Play, Win, Lose
}

data class Game(val board: Board) {
    val cells: Array<Board.Coordinate?> = arrayOfNulls(board.height.value * board.width.value)

    fun reveal(coordinate: Board.Coordinate): GameCommand {
        var index = cellIndex(coordinate)
        if (cells[index] != null) return GameCommand.Play

        cells[index] = board.cell(coordinate).also { cell ->
            if (cell::Class == Board.Mine) return GameCommand.Lose
            if (cell == CELL_WIDTH_NO_ADJACENT_MINES) {
                revealNeighbours(coordinate)
            }
        }

        return if (cells.count { it == null }) == board.mines.size) {
            GameCommand.Win
        } else {
            GameCommand.Play
        }

        private fun cellIndex(coordinate: Board.Coordinate): Int {
            return coordinate.y.value * board.width.value + coordinate.x.value
        }

        private fun revealNeighbours(coordinate: Board.Coordinate) {
            coordinate.neighbours(board.width, board.height).forEach { reveal(it) }
        }
    }
}

