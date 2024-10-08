package org.example


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
    var cells: MutableList<Cell?> = MutableList(board.height * board.width) { null }

    fun reveal(coordinate: Board.Coordinate): GameCommand {
        var index = cellIndex(coordinate)
        if (cells[index] != null) return GameCommand.Play

        val cell = board.cell(coordinate)
        cells[index] = cell

        if (cell is Cell.Mine) return GameCommand.Lose
        if (cell is Cell.Empty) {
            if (cell.neighbourMines == 0) {
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
        return coordinate.y * board.width + coordinate.x
    }

    private fun revealNeighbours(coordinate: Board.Coordinate) {
        coordinate.neighbours(board.width, board.height).forEach { reveal(it) }
    }
}

