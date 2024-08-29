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
        val listX = listOf(-1, 0, 1)
        val listY = listOf(-1, 0, 1)
        val neighbours: List<Coordinate> = 
            listX.flatMap { x -> listY.map { y -> x to y } }.filterNot {
                it == Pair(0, 0)
            }.map {
                (x, y) -> Coordinate(PositiveInt(x), PositiveInt(y))
            }

        operator fun plus(c: Coordinate): Coordinate {
            return Coordinate(
                x = PositiveInt(x.value + c.x.value),
                y = PositiveInt(y.value + c.y.value),
            )
        }

        fun neighbours(boardWidth: PositiveInt, boardHeight: PositiveInt): List<Coordinate> {
            return neighbours.map { n -> this + n }.filter {
                it.x.value < 0 || it.x.value >= boardWidth.value ||
                it.y.value < 0 || it.y.value >= boardHeight.value
            }
        }

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

