package org.example

data class PositiveInt(val value: Int) {
    init {
        require(value >= 0) { "正の数を入力してください" }
    }
}

data class Board(
    val width: PositiveInt,
    val height: PositiveInt,
    val mines: List<Coordinate>,
) {
    data class Coordinate(val x: PositiveInt, val y: PositiveInt)

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

