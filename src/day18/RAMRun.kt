package day18

import java.io.File

class RAMRun(file: File, ns: Int, dim: Int) {
    val rows = dim + 1
    val cols = dim + 1
    val map = buildList {
        for (i in 0..<rows) add(MutableList(cols) {"."})
    }
    val dirs = listOf(-1 to 0, 0 to 1, 1 to 0, 0 to -1)
    val coords = file.readLines().map { it.split(",").map { c -> c.toInt() }.reversed() }

    init {
        setMap(ns)
    }

    private fun setMap(ns: Int) {
        for (i in 0..<rows) {
            for (j in 0..<cols) {
                map[i][j] = "."
            }
        }

        coords.take(ns + 1).forEach { coord ->
            map[coord.first()][coord[1]] = "#"
        }
    }

    private fun getSteps(): Int {
//        map.forEach { println(it) }
        val q = ArrayDeque<Pair<Int, Int>>()
        var levels = -1
        val seen = mutableSetOf<Pair<Int, Int>>()
        q.add(0 to 0)

        while (q.isNotEmpty()) {
            val len = q.size
            levels++
//            println(q)
            for (i in 0..<len) {
                val cur = q.removeFirst()

                if (cur == rows - 1 to cols - 1) return levels

                dirs.map { (i, j) -> cur.first + i to cur.second + j }
                    .filter { (row, col) -> row in 0..<rows && col in 0..<cols && map[row][col] != "#" && row to col !in seen }
                    .forEach { (row, col) ->
                        seen.add(row to col)
                        q.add(row to col)
                    }

            }
        }
        return -1
    }

    fun part1() = getSteps()

    fun part2() = coords.indices.reversed().asSequence().filter { i ->
        setMap(i)
        getSteps() == -1
    }.map { i -> coords[i].reversed().joinToString(separator = ",") }
        .last()

}