package day14

import java.io.File
import kotlin.math.abs

class RestroomRedoubt(file: File, val rows: Int, val cols: Int) {
    private val robots = file.readLines().map { line ->
        val pattern = """p=(-?\d+),(-?\d+)\s+v=(-?\d+),(-?\d+)""".toRegex()
        val (startCol, startRow, velCol, velRow) = pattern.find(line)?.destructured!!
        Robot(startRow.toLong() to startCol.toLong(), velRow.toLong() to velCol.toLong())
    }

    private data class Robot(val start: Pair<Long, Long>, val velocity: Pair<Long, Long>) {
        var pos = start
        fun getQuadrant(times: Int, rows: Int, cols: Int): Int? {
            val row = (start.first + velocity.first * times).mod(rows)
            val col = (start.second + velocity.second * times).mod(cols)

            val quad =  when {
                (row < rows / 2 && col < cols / 2) -> 1
                (row < rows / 2 && col > cols / 2) -> 2
                (row > rows / 2 && col < cols / 2) -> 3
                (row > rows / 2 && col > cols / 2) -> 4
                else -> null
            }
//            println("$this at $row $col goes to quad $quad (${rows/2}, ${cols/2})")
            return quad
        }

        fun move(rows: Int, cols: Int): Pair<Long, Long> {
            pos = (pos.first + velocity.first).mod(rows).toLong() to (pos.second + velocity.second).mod(cols).toLong()
            return pos
        }
    }

    private fun Robot.manhattanDistance(b: Robot) = abs(this.pos.first - b.pos.first) + abs(this.pos.second - b.pos.second)

    private fun List<Robot>.entropy(): Long = (0..<this.size - 1).flatMap { i ->
        (i + 1..<this.size).map { j ->  robots[i].manhattanDistance(robots[j])}
    }.sumOf { it }

    fun part1(): Long = robots.mapNotNull { it.getQuadrant(100, rows, cols)}
        .groupingBy { it }
        .eachCount()
        .values
        .fold(1) {acc, b -> acc * b}

    fun part2(): Int {
        var minEntropyMoves = 0
        var minEntropy = robots.entropy()
        var christmasTree = robots.map { it.pos }.toSet()

        for (i in 1..<10_000) {
            robots.forEach { it.move(rows, cols) }
            val currEntropy = robots.entropy()

            if (currEntropy < minEntropy) {
                minEntropy = currEntropy
                christmasTree = robots.map { it.pos }.toSet()
                minEntropyMoves = i
            }
        }

        for (row in 0..<rows) {
            for (col in 0..<cols) {
                val char = if (row.toLong() to col.toLong() in christmasTree) "^" else " "
                print(char)
            }
            print("\n")
        }

        return minEntropyMoves
    }

}