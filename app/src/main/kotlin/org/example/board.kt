package org.example

import kotlin.math.max
import kotlin.math.abs


sealed interface Cell {
    class Mine : Cell
    class Empty(val neighbourMines: Int): Cell
}

data class Board(
    val width: Int,
    val height: Int,
    val mines: List<Coordinate>,
) {
    data class Coordinate(val x: Int, val y: Int) {
        operator fun plus(c: Coordinate): Coordinate {
            return Coordinate(x + c.x, y + c.y)
        }

        fun neighbours(boardWidth: Int, boardHeight: Int): List<Coordinate> {
            return NEIGHBOURS
                    .map { n -> this + n }
                    .filter { it.x in 0 until boardWidth && it.y in 0 until boardHeight }
        }

        fun neighbour(other: Coordinate): Boolean {
            return max(abs(x - other.x), abs(y - other.y)) <= 1
        }

        companion object {
            private val NEIGHBOURS = defineNeighbours()

            private fun defineNeighbours (): List<Coordinate> {
                val deltas = listOf(-1, 0, 1)
                return deltas.flatMap { dx ->
                    deltas.mapNotNull { dy ->
                        if (dx == 0 && dy == 0) null else Coordinate(dx, dy)
                    }
                }
            }
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

