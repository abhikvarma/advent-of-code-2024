package day10

import java.io.File

class Trail(file: File) {
    private val map: List<List<Int>> = file.readLines().map { line -> line.map { char -> char.digitToInt() } }
    private val rows = map.size
    private val cols = map.first().size
    private val dirs = listOf(1 to 0, 0 to 1, -1 to 0, 0 to -1)

    private fun Pair<Int, Int>.score(): Int {
        val peaks = mutableSetOf<Pair<Int, Int>>()
        val stack = ArrayDeque<Pair<Int, Int>>()
        val vis = mutableSetOf<Pair<Int, Int>>()

        stack.add(this)

        while (stack.isNotEmpty()) {
            val curr = stack.removeLast()

            if (curr in vis) continue

            if (map[curr.first][curr.second] == 9) peaks.add(curr)

            dirs.map { (x, y) -> x + curr.first to y + curr.second }
                .filter { (x, y) -> x in 0..<rows && y in 0..<cols }
                .filter { (x, y) -> map[x][y] == map[curr.first][curr.second] + 1 }
                .forEach { stack.add(it) }
        }
        return peaks.size
    }

    private fun Pair<Int, Int>.rating(): Int {
        var count = 0
        val stack = ArrayDeque<Pair<Int, Int>>()
        val vis = mutableSetOf<Pair<Int, Int>>()

        stack.add(this)

        while (stack.isNotEmpty()) {
            val curr = stack.removeLast()

            if (curr in vis) continue

            if (map[curr.first][curr.second] == 9) count++

            dirs.map { (x, y) -> x + curr.first to y + curr.second }
                .filter { (x, y) -> x in 0..<rows && y in 0..<cols }
                .filter { (x, y) -> map[x][y] == map[curr.first][curr.second] + 1 }
                .forEach { stack.add(it) }
        }
        return count
    }

    fun getTotalScore(): Int = (0..<rows)
        .flatMap { i ->
            (0..<cols).map { j  -> i to j }
        }
        .filter { (i, j) -> map[i][j] == 0 }
        .sumOf { it.score() }

    fun getTotalRating(): Int = (0..<rows)
        .flatMap { i ->
            (0..<cols).map { j -> i to j }
        }
        .filter { (i, j) -> map[i][j] == 0 }
        .sumOf { it.rating() }
}