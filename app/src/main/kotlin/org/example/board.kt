package org.example

import kotlin.math.max
import kotlin.math.abs


sealed interface Cell {
    class Mine : Cell
    class Empty(val neighbourMines: Int): Cell
}

val listX = listOf(-1, 0, 1)
val listY = listOf(-1, 0, 1)
val neighbours: List<Board.Coordinate> = listX.flatMap { x ->
        listY.map { y -> x to y }
    }.filterNot {
        it == Pair(0, 0)
    }.map {
        (x, y) -> Board.Coordinate(x, y)
    }

data class Board(
    val width: Int,
    val height: Int,
    val mines: List<Coordinate>,
) {
    data class Coordinate(val x: Int, val y: Int) {
        operator fun plus(c: Coordinate): Coordinate {
            return Coordinate(
                x = x + c.x,
                y = y + c.y,
            )
        }

        fun neighbours(boardWidth: Int, boardHeight: Int): List<Coordinate> {
            return neighbours.map { n -> this + n }.filter {
                it.x < 0 || it.x >= boardWidth ||
                it.y < 0 || it.y >= boardHeight
            }
        }

        fun neighbour(other: Coordinate): Boolean {
            return max(abs(x - other.x), abs(y - other.y)) <= 1
        }
    }

    fun cell(coordinate: Coordinate): Cell {
        return if (coordinate in mines) {
            Cell.Mine()
        } else {
            Cell.Empty(countNeighbours(coordinate))
        }
    }

    private fun countNeighbours(coordinate: Coordinate): Int {
        return mines.count { it.neighbour(coordinate) }
    }

    companion object {
        fun create(
            width: Int,
            height: Int,
            minesCount: Int,
        ): Board {
            val fullBoard: List<Coordinate> = (0 until width).flatMap { w ->
                (0 until height).map { h -> w to h }
            }.map {
                (x, y) -> Coordinate(x, y)
            }
            return Board(
                width = width,
                height = height,
                mines = fullBoard.shuffled().take(minesCount),
            )
        }
    }
}

