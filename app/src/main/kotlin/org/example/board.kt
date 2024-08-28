package org.example

import kotlin.math.max
import kotlin.math.abs

data class PositiveInt(val value: Int) {
    init {
        require(value >= 0) { "正の数を入力してください" }
    }
}

sealed interface Cell {
    class Mine : Cell
    class Empty(val neighbourMines: PositiveInt): Cell
}

data class Board(
    val width: PositiveInt,
    val height: PositiveInt,
    val mines: List<Coordinate>,
) {
    data class Coordinate(val x: PositiveInt, val y: PositiveInt) {
        fun neighbour(other: Coordinate): Boolean {
            return max(abs(x.value - other.x.value), abs(y.value - other.y.value)) <= 1
        }
    }

    fun cell(coordinate: Coordinate): Cell {
        return if (coordinate in mines) {
            Cell.Mine()
        } else {
            Cell.Empty(countNeighbours(coordinate))
        }
    }

    private fun countNeighbours(coordinate: Coordinate): PositiveInt {
        val count = mines.count { it.neighbour(coordinate) }
        return PositiveInt(count)
    }

    companion object {
        fun create(
            width: PositiveInt,
            height: PositiveInt,
            minesCount: PositiveInt,
        ): Board {
            val fullBoard: List<Coordinate> = (0 until width.value).flatMap { w ->
                (0 until height.value).map { h -> w to h }
            }.map {
                (x, y) -> Coordinate(PositiveInt(x), PositiveInt(y))
            }

            return Board(
                width = width,
                height = height,
                mines = fullBoard.shuffled().take(minesCount.value),
            )
        }
    }
}

