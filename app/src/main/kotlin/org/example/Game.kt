package org.example

val CELL_WIDTH_NO_ADJACENT_MINES = Cell.Empty(PositiveInt(0))

enum class GameCommand {
    Play, Win, Lose;

    fun toText(): String {
        return when (this) {
            Play -> "プレイ"
            Win -> "勝利"
            Lose -> "敗北"
        }
    }
}

data class Game(val board: Board) {
    var cells: MutableList<Cell?> = MutableList(board.height.value * board.width.value) { null }

    fun reveal(coordinate: Board.Coordinate): GameCommand {
        var index = cellIndex(coordinate)
        if (cells[index] != null) return GameCommand.Play

        cells[index] = board.cell(coordinate).also { cell ->
            println(cell)
            if (cell is Cell.Mine) return GameCommand.Lose
            if (cell == CELL_WIDTH_NO_ADJACENT_MINES) {
                revealNeighbours(coordinate)
            }
        }

        return if (cells.count { it == null } == board.mines.size) {
            GameCommand.Win
        } else {
            GameCommand.Play
        }
    }

    fun cell(coordinate: Board.Coordinate): Cell? {
        val cellIdx = cellIndex(coordinate)
        val cell = cells[cellIdx]
        cells[cellIdx] = cell

        return cell
    }

    fun isFinish(command: GameCommand): Boolean {
        return command == GameCommand.Win || command == GameCommand.Lose
    }

    private fun cellIndex(coordinate: Board.Coordinate): Int {
        return coordinate.y.value * board.width.value + coordinate.x.value
    }

    private fun revealNeighbours(coordinate: Board.Coordinate) {
        coordinate.neighbours(board.width, board.height).forEach { reveal(it) }
    }
}

